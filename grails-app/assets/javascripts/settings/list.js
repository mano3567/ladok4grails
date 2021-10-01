var settingsListModule = (function($) {
    "use strict";

    var initModule = function() {
        $("input[id^=value_]").off("change");
        $("input[id^=value_]").on("change", function (e) {
            var id = $(this).data('config-value-id');
            var newValue = $(this).val();
            $.ajax({
                type: "POST",
                url: "/settings/updateValue",
                data: { id: id, newValue: newValue },
                success: function(data) {
                    $("div#updated_"+id).html(data);
                },
                error: function(data) {
                    console.log("Some problem updating value.");
                },
                complete: function() {
                }
            });
        });
    };

    return {initModule: initModule};
}(jQuery));

jQuery(document).ready(
    function() {
        settingsListModule.initModule(jQuery("body"));
    }
);

