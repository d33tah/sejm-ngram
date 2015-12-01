
module.directive('chartPane', function () {
    return {
        restrict: 'E',
        scope: {
            graph: '=',
            currentRange: '='
        },
        controller: function ($scope) {
            this.scope = $scope; // for rangeSelector, which watches scaleX
        },
        link: function link(scope, iElement, iAttrs, controller) {
          // this function gets executed long before the graph shows
          var id = iElement.attr('id');
          scope.idPrefix = id + '-';
          var svg = d3.select('#' + id);
          var chartMargin = {
              top: 10,
              bottom: 10,
              left: 10,
              right: 10
          };
          // chart includes the axes, linesCanvas does not, and it is the area lines are plotted
          var linesCanvasMargin = {
              top: 40,
              bottom: 40,
              left: 40,
              right: 40
          };
          var svgWidth = iElement.attr('width');
          var svgHeight = iElement.attr('height');
          var chartWidth = svgWidth - chartMargin.left - chartMargin.right;
          var chartHeight = svgHeight - chartMargin.top - chartMargin.bottom;
          var linesCanvasWidth = chartWidth - linesCanvasMargin.left - linesCanvasMargin.right;
          var linesCanvasHeight = controller.linesCanvasHeight = 
              chartHeight - linesCanvasMargin.top - linesCanvasMargin.bottom;
          svg.select('clipPath rect').attr({
              width: linesCanvasWidth,
              height: linesCanvasHeight
          });

          var chart = svg.select('g.chart');
          chart.attr({
                  width: chartWidth,
                  height: chartHeight,
                  transform: 'translate(' + chartMargin.left + ', ' + chartMargin.top + ')'
              });

          var linesCanvas = svg.select('g.linesCanvas');
          linesCanvas.attr({
              width: linesCanvasWidth,
              height: linesCanvasHeight,
              transform: 'translate(' + linesCanvasMargin.left + ', ' + linesCanvasMargin.top + ')'
          });
          scope.axisX = chart.append('g')
              .attr({
                  'class': 'axis axisX',
                  'transform': 'translate(' + linesCanvasMargin.left + ', ' + (linesCanvasHeight + linesCanvasMargin.top) + ')'
              });
          scope.axisY = chart.append('g')
              .attr({
                  'class': 'axis axisY',
                  'transform': 'translate(' + linesCanvasMargin.left + ', ' + linesCanvasMargin.top + ')'
              });

          scope.scaleX = d3.time.scale()
              .range([0, linesCanvasWidth]);
          scope.scaleY = d3.scale.linear()
              .range([linesCanvasHeight, 0]);
          controller.scaleX = scope.scaleX;

          function updateAxes() {
              // it also updates scales, rangeSelector is watchint scaleX
              scope.scaleX.domain(scope.currentRange);
              scope.scaleY.domain(scope.graph.yRange);

              var axisXFunction = d3.svg.axis()
                  .scale(scope.scaleX)
                  .orient('bottom')
                  .ticks(4);
              var axisYFunction = d3.svg.axis()
                  .scale(scope.scaleY)
                  .orient('left').ticks(linesCanvasHeight/28);

              scope.axisX.call(axisXFunction);
              scope.axisY.call(axisYFunction);
          }
          scope.$watchGroup(['currentRange', 'graph.yRange'], updateAxes, true);
        },
        templateUrl: '/templates/chartPane.html',
        replace: true,
        transclude: true
    };
});

/* Directive that decorates the parent chart pane with a brush range selector */
module.directive('rangeSelector', function () {
    return {
        restrict: 'E',
        require: '^chartPane',
        scope: {
            currentRange: '=' // two-way binding
        },
        link: function (scope, iElement, iAttrs, chartPane) {
          var initialized = false;
          var brush;
          function initBrushRangeSelector (scaleX) {
              initialized = true;
              brush = d3.svg.brush().x(scaleX);

              // NOTE: if we don't create the brush under the parent, the range is not selectable
              // (The rect object for the brush has no size), I'd like to know the cause.
              // to repro: remove '.parent()' from the line
              var parent = d3.select(iElement.parent()[0]);

              var g = parent.append("g").attr({'class': 'brush'});
              g.call(brush)
                  .selectAll("rect")
                  .attr("height", chartPane.linesCanvasHeight);

              // to start off, range is equal to scaleX of the parent chart pane, and the brush is empty
              scope.currentRange = scaleX.domain();
              brush.on('brush', function () {
                  var newRange = brush.extent();
                  if (newRange[0].valueOf() === newRange[1].valueOf()) {
                    newRange = chartPane.scope.scaleX.domain();
                    brush.clear(); // TODO does not seem to work, there is a hair width brush still there
                  }
                  if (newRange !== scope.currentRange) {
                    scope.currentRange = newRange;
                    scope.$apply();
                  }
              });
          }
          function update() {
              var scaleX = chartPane.scope.scaleX;
              if (scaleX) {
                if (!initialized)
                    initBrushRangeSelector(scaleX);
                // that means a new search was executed
                scope.currentRange = scaleX.range();
                brush.clear(); // TODO untested
                brush.x(scaleX);
              }
          }
          chartPane.scope.$watch('scaleX', update, true);
        },
        replace: true,
        template: "<g></g>" // g is not used, see the NOTE in initBrushRangeSelector
    }
});

module.filter('encodeSvgPath', function () {
    return function(occurences, scaleX, scaleY, cacheId) {
			  var cacheKey = "" + scaleX.domain() + "-" + scaleY.domain();
			  var cached = null;
			  if (cacheId in occurences) {
					var cache = occurences[cacheId];
					if (cache.key == cacheKey) {
						cached = cache.val;
					}
				}
			  if (cached == null) {
					var lineFunction = d3.svg.line()
							.x(function (o) { return scaleX(o.date); })
							.y(function (o) { return scaleY(o.count); });
					cached = lineFunction(occurences);
					occurences[cacheId] = {key: cacheKey, val: cached};
				}
        return cached;
    };
})
