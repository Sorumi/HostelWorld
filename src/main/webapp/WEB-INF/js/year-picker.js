/**
 * Created by Sorumi on 17/3/5.
 */
(function ($) {
    $.fn.yearpicker = function (options) {

        //var $this = this;

        var html = '<button type="button" class="mp-prev-button fa fa-angle-left"></button> <input class="mp-date" readonly="readonly"> <button type="button" class="mp-next-button fa fa-angle-right"></button>';
        var defaultSettings = {
            //'numberToDestory': 0,
            'onChange': function (date) {
            },
            'onDestroy': function () {

            }
        };
        var settings = $.extend({}, defaultSettings, options);
        var disableClass = 'button-disabled';


        return this.each(function () {
                //var $this = $(this);
                $(this).html(html);
                //
                var min = $(this).attr('min');
            if (min == undefined) {
                min = settings.min;
            }
                var max = $(this).attr('max');
            if (max == undefined) {
                max = settings.max;
            }
                var defaultDate = $(this).attr('default');
                if (!defaultDate) {
                    defaultDate = new Date().getFullYear();
                }

                var prevButton = $(this).children('.mp-prev-button');
                var nextButton = $(this).children('.mp-next-button');
                var dateInput = $(this).children('.mp-date');
                dateInput.val(defaultDate);
                var dateStr = dateInput.val();
                var date = Number(dateStr);

                var name = $(this).attr('name');
                if (name) {
                    dateInput.attr('name', name);
                }
                //
                function dateDidChange() {
                    var prevDate = date - 1;
                    var nextDate = date + 1

                    if (prevDate < min) {
                        prevButton.addClass(disableClass);
                    }
                    if (!(prevDate < min)) {
                        prevButton.removeClass(disableClass);
                    }
                    if (nextDate > max) {
                        nextButton.addClass(disableClass);
                    }
                    if (!(nextDate > max)) {
                        nextButton.removeClass(disableClass);
                    }
                    settings.onChange(date);
                }

                prevButton.bind('click', function () {
                    var newDate = date - 1;

                    if (!(newDate < min)) {
                        date = newDate;
                        dateInput.val(date);
                        dateDidChange();
                    }

                });

                nextButton.bind('click', function () {
                    var newDate = date + 1;

                    if (!(newDate > max)) {
                        date = newDate;
                        dateInput.val(date);
                        dateDidChange();
                    }
                });

                dateDidChange();
            }
        )
    }
})(jQuery);
