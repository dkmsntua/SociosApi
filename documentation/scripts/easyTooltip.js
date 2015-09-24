(function($){
	$.fn.easyTooltip = function(options){
		var defaults = {
			xOffset : 10,
			yOffset : 25,
			tooltipId : "easyTooltip",
			clickRemove : false,
			content : "",
			useElement : ""
		};
		var options = $.extend(defaults, options);
		var content;
		this.each(function(){
			var title = '';
			if ($(this).hasClass('mapMethod'))
			{
				var method = $(this).text();
				title = $('#' + method + '').get(0).innerHTML;
			}
			else if ($(this).hasClass('mapSn'))
			{
				title = '';
			}
			else
			{
				var method = $(this).parent().children().first().html();
				var number = $(this).index();
				var sn = $('#map tr').last().find('td:eq(' + number + ')').html();
				var token = method + "-" + sn;
				var html = $('#' + token + '');
				if (html.length == 0)
				{
					title = 'not implemented';
				}
				else if (!html.text().trim())
				{
					title = 'no comments';
				}
				else
				{
					title = html.text();
				}
			}
			$(this).hover(
					function(e){
						content = (options.content != "") ? options.content : title;
						content = (options.useElement != "") ? $("#" + options.useElement).html() : content;
						$(this).attr("title", "");
						if (content != "" && content != undefined)
						{
							$("body").append("<div id='" + options.tooltipId + "'>" + content + "</div>");
							$("#" + options.tooltipId).css("position", "absolute").css("top", (e.pageY - options.yOffset) + "px").css("left", (e.pageX + options.xOffset) + "px")
									.css("display", "none").fadeIn("fast")
						}
					}, function(){
						$("#" + options.tooltipId).remove();
						$(this).attr("title", title);
					});
			$(this).mousemove(function(e){
				$("#" + options.tooltipId).css("top", (e.pageY - options.yOffset) + "px").css("left", (e.pageX + options.xOffset) + "px")
			});
			if (options.clickRemove)
			{
				$(this).mousedown(function(e){
					$("#" + options.tooltipId).remove();
					$(this).attr("title", title);
				});
			}
		});
	};
})(jQuery);
