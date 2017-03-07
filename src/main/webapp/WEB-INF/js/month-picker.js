/**
 * Created by Sorumi on 17/3/5.
 */
(function ($) {
    $.fn.monthpicker = function (options) {

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
                var minDate = new Date(min);
                var max = $(this).attr('max');
                if (max == undefined) {
                    max = settings.max;
                }
                var maxDate = new Date(max);
                var defaultDate = $(this).attr('default');
                if (!defaultDate) {
                    defaultDate = $.datepicker.formatDate("yy-mm", new Date());
                }

                var prevButton = $(this).children('.mp-prev-button');
                var nextButton = $(this).children('.mp-next-button');
                var dateInput = $(this).children('.mp-date');
                dateInput.val(defaultDate);
                var dateStr = dateInput.val();
                var date = new Date(dateStr);

                var name = $(this).attr('name');
                if (name) {
                    dateInput.attr('name', name);
                }
                //
                function dateDidChange() {
                    var prevDate = new Date(date);
                    prevDate.setMonth(date.getMonth() - 1);

                    var nextDate = new Date(date)
                    nextDate.setMonth(date.getMonth() + 1);

                    if (prevDate < minDate) {
                        prevButton.addClass(disableClass);
                    }
                    if (!(prevDate < minDate)) {
                        prevButton.removeClass(disableClass);
                    }
                    if (nextDate > maxDate) {
                        nextButton.addClass(disableClass);
                    }
                    if (!(nextDate > maxDate)) {
                        nextButton.removeClass(disableClass);
                    }
                    settings.onChange(date);
                }

                prevButton.bind('click', function () {
                    var newDate = new Date(date);
                    newDate.setMonth(date.getMonth() - 1);

                    if (!(newDate < minDate)) {
                        dateStr = $.datepicker.formatDate("yy-mm", newDate);
                        date = newDate;
                        dateInput.val(dateStr);
                        dateDidChange();
                    }

                });

                nextButton.bind('click', function () {
                    var newDate = new Date(date);
                    newDate.setMonth(date.getMonth() + 1);

                    if (!(newDate > maxDate)) {
                        dateStr = $.datepicker.formatDate("yy-mm", newDate);
                        date = newDate;
                        dateInput.val(dateStr);
                        dateDidChange();
                    }
                });

                dateDidChange();
            }
        )
    }
})(jQuery);
