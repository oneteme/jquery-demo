import { loadJson } from "./utils.js";

export function loadDialects() {
    $("#db-select-popup").empty()
    loadJson("/dialect.json").then(dialects => {
        $("#db-select-popup").append(
            $("<div>", { class: "db-select-popup-arrow" })
        )
        $.each(dialects, (key, value) => {
            let div;
            switch (value.type) {
                case "div":
                    const type = value.metadata.type,
                        content = value.metadata.content,
                        attributes = value.metadata.attr;
                    div = $("<" + type + ">", attributes).html(content ?? "")
                    break;
                case "icon":
                    div = $("<i>", { class: value.metadata })
                    break;

                case "img":
                    div = $("<img>", { src: value.metadata, class: "img-class" })
                    break;

                // case "svg":

                //     break;

                // case "html":

                //     break;

                default:
                    break;
            }
            $("#db-select-popup").append(
                $("<div>", { class: "db-option", "data-value": value.value, "data-label": value.label })
                    .append(
                        div,
                        $("<span>").html(value.label)
                    )
            )
        })
        if (localStorage.getItem("jarvis.demo.dialect")) {
            setOptionActive($(".db-option[data-value=" + localStorage.getItem("jarvis.demo.dialect") + "]"))
        }
        else {
            setOptionActive($(".db-option:first"));
        }
    })
}

$(document).on(
    "click",
    "#db-select-wrapper",
    (e) => {
        e.stopPropagation();
        $('#db-select-popup').toggleClass('show');
        $(e.currentTarget).toggleClass('active');
    });

$(document).on('click', '.db-option', (e) => {
    setOptionActive($(e.currentTarget));
    // Change SQL or database code
});

function setOptionActive(option) {
    const value = option.data('value'),
        label = option.data('label'),
        icon = option.children().first().clone();

    $('.db-option').removeClass('active');
    option.addClass('active');
    $('#db-select-label').html(label);
    $('#db-select-icon-slot').html(icon);
    localStorage.setItem("jarvis.demo.dialect", value);
}
