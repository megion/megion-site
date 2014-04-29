/**
 * 
 */
(function(a) {
	jQuery.fn.sliderBox = function(config) {
		var x = a(this);
		var containerDiv = x.children(".container");
		var listTable = containerDiv.children(".list");
		var listItems = listTable.find(".item");
		var conf = config || {};
		var speed = conf.speed || "slow";
		// минимальная ширина элемента должна быть не меньше чем
		// ширина любого из неделимых блоков (картинка, текст без пробелов)
		// для всех элементов, иначе подсчет страниц будет работать неверно
		var minWidth = conf["min-width"] || 120;
		var scrollable = conf.scrollable || false;
		var hiddenClass = "hidden", invisibleClass = "invisible";
		var enablePopupInfo = conf.enablePopupInfo || false;
		var g = document.onresize ? document : window;
		var page = {
			first : 0,
			last : 0,
			length : listItems.length
		};
		if (scrollable) {
			x.addClass("narrowed");
			containerDiv
					.before('<div class="arrows"><i class="prev" unselectable="on"><i class="triangle disabled">&lsaquo;</i></i><i class="next" unselectable="on"><i class="triangle">&rsaquo;</i></i></div>');
		}
		var arrowsContainer = x.find(".arrows > i");
		var leftArrow = arrowsContainer.eq(0);
		var rightArrow = arrowsContainer.eq(1);
		listItems.bind(
				"slide.show",
				function() {
					a(this).removeClass(invisibleClass).find("img[data-src]")
							.each(
									function() {
										var c = a(this);
										c.attr("src", c.attr("data-src"))
												.removeAttr("data-src");
									});
				}).bind("slide.hide", function() {
			a(this).addClass(invisibleClass);
		});
		function initSlideBox() {
			if (x && !x.hasClass("slide-box")) {
				x.addClass("slide-box");
				listTable.find("." + hiddenClass).removeClass(hiddenClass);
			}
			// ширина контейнера
			var containerWidth = containerDiv.width();
			if (containerWidth == 0) {
				return false;
			}
			// число колонок на которые будет разбит контейнер
			var columnCount = Math.floor(containerWidth / minWidth);
			if (columnCount > page.length) {
				columnCount = page.length;
			}
			page.last = page.first + columnCount - 1;
			var h = Math.min(page.first, page.length - columnCount);
			if (h != page.first) {
				page.first = h;
				page.last = page.first + columnCount - 1;
			}
			f();
			listItems.children("div.item-container").css({
				width : Math.ceil(containerWidth / columnCount) + "px"
			});
			var listTableHeight = 200;
			if (listTable.height() > 0) {
				listTableHeight = listTable.height();
			}
			containerDiv.css({
				height : listTableHeight + "px"
			});
			listTable.css({
				left : "-" + listItems.get(h).offsetLeft + "px"
			});
			b();
			A();
			if (page.length <= columnCount) {
				arrowsContainer.hide();
			} else {
				arrowsContainer.show();
			}

			if (enablePopupInfo) {
				initPopupInfo(listTableHeight);
			}
		}
		a(g).resize(initSlideBox);
		a(initSlideBox);
		function d(h) {
			var l = page.last - page.first;
			if (h < 0) {
				h = 0;
			}
			if (h >= listItems.length - l) {
				h = listItems.length - l - 1;
			}
			var c = listItems.eq(h);
			if (c.length == 1) {
				page.last = h + page.last - page.first;
				page.first = h;
				f();
				listTable.stop().animate({
					"margin-left" : "-" + c.get(0).offsetLeft + "px"
				}, speed, "swing", function() {
					A();
					b();
				});
			}
		}
		function A() {
			if (page.first == 0) {
				leftArrow.children(".triangle").addClass("disabled");
			}
			if (page.first > 0) {
				leftArrow.children(".triangle").removeClass("disabled");
			}
			if (page.last == page.length - 1) {
				rightArrow.children(".triangle").addClass("disabled");
			}
			if (page.last < page.length - 1) {
				rightArrow.children(".triangle").removeClass("disabled");
			}
		}
		function f() {
			listItems.slice(page.first, page.last + 1).trigger("slide.show");
		}
		function b() {
			listItems.slice(0, Math.max(0, page.first - 1)).trigger(
					"slide.hide");
			listItems.slice(page.last + 1, listItems.length).trigger(
					"slide.hide");
		}
		function back() {
			var shift = page.last - page.first;
			if (shift == 0) {
				// поправка когда виден только один слайдер, то делаем
				// минимальный сдвиг влево на 1
				shift = 1;
			}
			var normShift = Math.max(0, page.first - shift);
			d(normShift);
		}
		leftArrow.click(function() {
			if (!a(this).children(".triangle").hasClass("disabled")) {
				back();
			}
		});
		function next() {
			var shift = page.last - page.first;
			var additionShift = 0;
			if (shift == 0) {
				// поправка когда виден только один слайдер, то делаем
				// минимальный сдвиг влево на 1
				// shift = 1;
				additionShift = 1;
			}
			var normShift = Math.min(page.last + additionShift, page.length
					- shift - 1);
			d(normShift);
		}
		rightArrow.click(function() {
			if (!a(this).children(".triangle").hasClass("disabled")) {
				next();
			}
		});
		if (scrollable) {
			// required library jquery.mousewheel.js
			containerDiv.mousewheel(function(c, h) {
				d(page.first - Math.round(Math.abs(h) / h));
				return false;
			});
		}
		arrowsContainer.mousedown(function(event) {
			event.preventDefault();
			return false;
		});

		function initPopupInfo(_listTableHeight) {
			// изменяем html для всех найденных .popup-info
			listTable.find(".item").each(
					function() {
						var item = jQuery(this);
						var popupInfo = item
								.find('.popup-container .popup-info');

						var popupInfoLeftMargin = -(popupInfo.width() - item
								.width()) / 2;
						popupInfo.css({
							'bottom' : _listTableHeight + 'px',
							'margin-left' : popupInfoLeftMargin + 'px'
						});
					});

			listTable.find(".item .popup-hover").each(function() {
				jQuery(this).hover(function() {
					jQuery(this).parent().children('.popup-info').fadeIn(200);
				}, function() {
					jQuery(this).parent().children('.popup-info').fadeOut(200);
				});
			});
		}

	};
})(window.jQuery);