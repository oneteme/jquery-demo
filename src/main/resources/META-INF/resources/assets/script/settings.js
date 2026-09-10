import * as utils from "./utils.js"

export function loadSettings() {
    fetch("/grid_settings.json")
        .then((response) => response.json())
        .then((data) => {
            
            createSettings(data);
        });
}

function createSettings(data) {
    $.each(data, (key, val) => {
        let elemCount = parseInt(val.count);
        $(".options-container").append(
            $("<div>", {
                class: "options-content grid-options",
                "data-count": elemCount,
            })
        );
        $.each(val.items, (key, val) => {
            let elemClass = val.class,
                gridDiv = $(".grid-options[data-count='" + elemCount + "'");
            gridDiv.append(
                $("<div>", {
                    class: "option-box " + elemClass,
                    "data-class": elemClass,
                })
            );
            for (let index = 0; index < elemCount; index++) {
                gridDiv
                    .find(".option-box:last")
                    .append($("<div>", { class: "display-grid-box" }));
            }
        });
    });
}

$(document).on("click", ".grid-options .option-box", (e) => {
    let appliedClass = $(e.currentTarget).attr("data-class");
    utils.removeSecondClass($(".settings-content"));
    $(".settings-content").addClass(appliedClass);
});

$(".position-options .option-box").on("click", (e) => {
    utils.swapDivsOnce($(e.currentTarget));
});