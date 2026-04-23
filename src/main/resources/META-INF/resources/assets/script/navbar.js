import { javaMdFiles } from "./files.js";

var navStyleFields = {};
export function loadNavData(path = "/menu.json", element = $(".navbar-nav")) {
    return new Promise(res => {
        fetch(path)
            .then((response) => response.json())
            .then((data) => {
                createNavbar(data, element);
                res();
            })
    })
}

function createNavbar(data, element) {
    $.each(data, (key, nav_element) => {
        if ("items" in nav_element) {
            element.append(
                $("<div>", {
                    class: "parent-element",
                }).append(
                    $("<div>", {
                        class: "parent_title",
                        isloaded: false,
                        "sub-menu": nav_element.items,
                    }).append(
                        $("<span>").html(nav_element.title),
                        $("<img>", {
                            class: "nav-accordion accordion",
                            src: "/assets/images/accordion.svg",
                        })
                    ),
                    $("<div>", {
                        class: "sub-nav",
                        "data-title": nav_element.title,
                        style: "display:none;",
                    })
                )
            );
        } else {
            let seperatedTutoArr = setupNext(data, key);
            setupNavItem(
                nav_element,
                element,
                seperatedTutoArr[0],
                seperatedTutoArr[1]
            );
            tippy("li[data-tippy-content]", {
                animation: 'scale',
                placement: 'top',
                arrow: true,
                theme: 'jarvis'

            });
        }
    });
}

function setupNavItem(navItem, divElement, next, prev) {
    const javapath = (divElement.attr("data-title") ? divElement.attr("data-title").toLowerCase() + "/" : "") + (navItem.label ?? navItem.title).toLowerCase() + ".md",
        navFields = {
            class: "nav-item jq-example",
            "data-learn": navItem.title.toLowerCase(),
            "data-next": next,
            "data-prev": prev
        };
    console.log("javapath : ", javapath)
    console.log("javaMdFiles : ", javaMdFiles)
    if (javaMdFiles.includes(javapath)) {
        navFields["data-java"] = javapath;
    }
    if (navItem.tooltip) {
        navFields["data-tippy-content"] = navItem.tooltip;
    }
    // delete navItem.title;
    $.each(navItem, (key, value) => {
        if (key == "examples") {
            value = JSON.stringify(value);
        }
        navFields["data-" + key] = value;
    });
    divElement.append($("<li>", navFields).html(navItem.title));
}

function setupNext(arr, key) {
    let next =
        key + 1 < arr.length && !("items" in arr[key + 1])
            ? arr[key + 1].title
            : null,
        prev =
            key - 1 >= 0 && !("items" in arr[key - 1]) ? arr[key - 1].title : null;
    return [next, prev];
}

export function toggleNavBar(animationTime = 100) {
    $(".navbar-container").animate(navStyleFields, animationTime, () => {
        $(".navbar-container").css("display", navStyleFields.display);
    }); // duration in milliseconds
}

export function hideNavBar(element = $("#jq-show-examples")) {
    navStyleFields = {
        height: "0",
        display: "none",
    };
    element.attr("data-show", "show");
    element.find("img:first").show();
    element.find("img:last").hide();
    // element.animate(navStyleFields, 300, () => {
    //     element.find("img:first").css("width",)
    //     element.find("img:last").hide();
    // });
    toggleNavBar();
}

export function showNavBar(element = $("#jq-show-examples")) {
    navStyleFields = {
        height: "100%",
        display: "block",
    };
    element.attr("data-show", "hide");
    element.find("img:first").hide();
    element.find("img:last").show();
    // element.animate(navStyleFields, 300, () => {
    //     element.find("img:first").hide();
    //     element.find("img:last").show();
    // });
    toggleNavBar();
}

export function toggleNavSubElements(e, subElement = ".sub-nav") {
    if ($(e.currentTarget).find(".accordion").hasClass("rot-accordion")) {
        $(e.currentTarget).find(".accordion").removeClass("rot-accordion");
        $(e.currentTarget).siblings(subElement).hide(".sub-nav");
    } else {
        $(e.currentTarget).find(".accordion").addClass("rot-accordion");
        $(e.currentTarget).siblings(subElement).show();
    }
}

$(document).on(
    "click",
    ".navbar-container .parent_title[isloaded='false']",
    (e) => {
        console.log("clicked on a unloaded menu");
        const subMenuFile = "subMenu/" + $(e.currentTarget).attr("sub-menu"),
            title = $(e.currentTarget).find("span:first").html(),
            subNavDiv = $(".sub-nav[data-title='" + title + "']");
        loadNavData(subMenuFile, subNavDiv).then(() => {
            $(e.currentTarget).attr("isloaded", true);
            toggleNavSubElements(e);
        })
    }
);

$(document).on(
    "click",
    ".navbar-container .parent_title[isloaded='true']",
    (e) => {
        console.log("clicked on a loaded menu");
        if (!$(event.target).closest(".jq-example").length) {
            toggleNavSubElements(e);
        }
    }
);