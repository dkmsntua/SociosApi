function setSelection(el){
	var items = el.parent().children();
	items.each(function(){
	if ($(this).hasClass('selected')){
			$(this).removeClass('selected');
		}
	});
	el.addClass('selected');
}
function getHTML(list, id){
	var result;
	$('#' + list + '').children().each(function(){
		if ($(this).attr('id') == id){
			result = $(this).get(0).innerHTML;
			return;
		}
	});
	return result;
}
function fadeChange(list, num, value, callback){
	$('#' + list + '').fadeOut(num, function(){
		$(this).html(value).fadeIn(num, callback);
	});
}
function getSelected(list){
	var result;
	$('#' + list + '').children().each(function(){
		if ($(this).hasClass('selected')){
			result = $(this).text();
			return;
		}
	});
	return result;
}
function changeComment(sn, method){
	var id = method + "-" + sn;
	var comment = getHTML('commentsPool', id);
	if (comment == null){
		comment = $('#notImplemented').get(0).innerHTML;
	}
	else if (!comment.trim()){
		comment = $('#noComment').get(0).innerHTML;
	}
	comment = "<h4>" + sn + "</h4>" + comment;
	fadeChange('commentsDisplay', 250, comment);
}
function getRandomMethod(){
	var totalMethods = $('#left').children().length;
	var randomNumber = Math.floor(Math.random() * totalMethods);
	return $('#left > li:eq(' + randomNumber + ')');
}
function getRandomSn(){
	var totalSns = $('#sns').children().length;
	var randomNumber = Math.floor(Math.random() * totalSns);
	return $('#sns > div:eq(' + randomNumber + ')');
}
function setInfo(el){
	setSelection(el);
	var id = el.text();
	var html = getHTML('methodPool', id);
	fadeChange('infoDisplay', 250, html);
}
// objects
function openMenu(el, cb){
	var number = 0;
	el.parent().find('ul').each(function(index){
		if ($(this).is(":visible")){
			number = index;
		}
	});
	var open = el.parent().find('ul:eq(' + number + ')').slideUp('500', function(){
		el.next().slideDown();
		cb();
	});
}
function changeObject(el){
	setSelection(el);
	var id = el.attr('id');
	var node = $('#table-' + id + '').clone(true);
	if (node.length)
	{
		$('#objectsHolder').fadeOut(500, function(){
			$(this).empty();
			$(this).append(node).fadeIn(500);
		});
	}
}