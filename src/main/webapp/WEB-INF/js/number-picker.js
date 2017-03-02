/**
 * Created by Sorumi on 17/3/1.
 */
(function ($) {
    $.fn.numberpicker = function (options) {

        var $this = this;

        var html = '<button type="button" class="np-sub-button">-</button> <input class="np-number"> <button type="button" class="np-add-button">+</button>';
        var defaultSettings = {
            'numberToDestroy': 0,
            'onChange': function (num) {
            },
            'onDestroy': function () {

            }
        };
        var settings = $.extend({}, defaultSettings, options);
        var disableClass = 'np-button-disabled';


        return this.each(function () {
                var $this = $(this);
                $(this).html(html);

                var min = $(this).attr('min');
                var max = $(this).attr('max');
                var defaultNum = $(this).attr('default');
                var name = $(this).attr('name');
                if (!defaultNum) {
                    defaultNum = 1;
                }

                var subButton = $(this).children('.np-sub-button');
                var addButton = $(this).children('.np-add-button');
                var numInput = $(this).children('.np-number');
                numInput.val(defaultNum);
                var num = numInput.val();

                if (name) {
                    numInput.attr('name', name);
                }

                function numDidChange() {

                    if (num - 1 < min) {
                        subButton.addClass(disableClass);
                    }
                    if (!(num - 1 < min)) {
                        subButton.removeClass(disableClass);
                    }
                    if (num + 1 > max) {
                        addButton.addClass(disableClass);
                    }
                    if (!(num + 1 > max)) {
                        addButton.removeClass(disableClass);
                    }

                }

                subButton.bind('click', function () {
                    if (num - 1 == settings.numberToDestroy) {
                        $this.html('');
                        settings.onDestroy();
            }
                    if (!(num - 1 < min)) {
                        num--;
                        numInput.val(num);
                        numDidChange();
                        settings.onChange(num);
                    }


                });

                addButton.bind('click', function () {
                    if (!(num + 1 > max)) {
                        num++;
                        numInput.val(num);
                        numDidChange();
                        settings.onChange(num);
                    }
                });

                numDidChange();
            }
        )
    }
})(jQuery);
