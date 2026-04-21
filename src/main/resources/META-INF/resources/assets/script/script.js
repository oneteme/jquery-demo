import { getAllJavaMd } from "./files.js";
import { introNextStep, introNextStepCondition, isIntro } from "./intro-jq.js";
import { hideNavBar, loadNavData, showNavBar, toggleNavBar, toggleNavSubElements } from "./navbar.js";
import { loadSettings } from "./settings.js";
import { hideLoading, showError, showLoading, showSuccess } from "./status.js";
import { clearTable, displayTableResults } from "./table.js";
import * as utils from "./utils.js";

var inputTimeOut;
//**************** EVENT LISTENERS ****************/
$(document).ready(function () {
  hljs.highlightAll();
  $(".navbar-nav").empty();
  getAllJavaMd().then(mdFiles => loadNavData())
  loadSettings();
  loadViews();
  $("#jq-execute").on("click", (e) => {
    introNextStep(100);
    // $("#query-form").submit();
  });
  $("#query-form").on("submit", (e) => {
    e.preventDefault(); // Prevent the default form submission
    console.log("fetchJQ from submitting form")
    fetchJQData();
  });
  $("#jq-table").on("change", (e) => {
    $("#jq-columns").val("");
    $("#jq-filters").val("");
    console.log("fetchJQ from changing table at form")
    fetchJQData();
    introNextStepCondition($("#jq-table").val() === "customers");
  });

  $("#jq-show-examples").on("click", (e) => {
    if ($(e.currentTarget).attr("data-show") == "show") {
      showNavBar();
    } else {
      hideNavBar();
    }
    toggleNavBar();
    introNextStep();
  });

  $(document).on("click", ".jq-params .parent_title", (e) => {
    console.log("clicked on a loaded menu");
    if (!$(event.target).closest(".jq-column").length) {
      toggleNavSubElements(e, ".select-columns");
    }
  });

  $(document).on("click", ".jq-example", (e) => {
    $(".content.definition-display").show();
    $(".content.jquery-display").hide();
    loadExample($(e.currentTarget));
    if (!$("#main-title").attr("show")) {
      $("#main-title").attr({ "show": ".content.jquery-display,.show-docs", "hide": ".content" })
    }
  });

  $("#jq-columns").on("input", (e) => {
    clearTimeout(inputTimeOut);
    inputTimeOut = setTimeout(() => {
      introNextStepCondition($(e.currentTarget).val() === "country,name,contact,id", 0)
    }, 800);
  });
  $("#jq-filters").on("input", (e) => {
    clearTimeout(inputTimeOut);
    inputTimeOut = setTimeout(() => {
      introNextStepCondition($(e.currentTarget).val() === "country=France", 0)
    }, 800);
  });
  $(".jqsyntax-container input").on("blur", (e) => {
    if (!isIntro()) $("#query-form").submit();
  });
  $(".show-docs").on("click", (e) => {
    introNextStep();
  });
  $(".close_window_btn").on("click", (e) => {
    $(e.currentTarget).parent().hide();
  });
  $(".btn-trigger").on("click", (e) => {
    let elementToHide = $(e.currentTarget).attr("hide"),
      elementToShow = $(e.currentTarget).attr("show"),
      elementAtCursor = $(e.currentTarget).attr("cursor-data");
    if (elementToHide) {
      $.each(elementToHide.split(","), (key, element) => {
        $(element).hide();
      });
    }
    if (elementToShow)
      $.each(elementToShow.split(","), (key, element) => {
        $(element).show();
      });

    if (elementAtCursor)
      $.each(elementAtCursor.split(","), (key, element) => {
        utils.displayDivAtCursor($(element), e);
      });
  });
  $(".definition-element .show-demo").on("click", (e) => {
    $(".example-number:first").click();
    introNextStep();
  });
  $(".tuto_btn_container").on("click", (e) => {
    let example = $(e.currentTarget).attr("data-example");
    $(".jq-example[data-learn='" + example + "']").click();
  });
});


//**************** FUNCTIONS ****************/
function loadViews() {
  fetch("/views.json")
    .then((response) => response.json())
    .then((data) => {
      $.each(data, (key, value) => {
        let view = value["view"],
          viewLib = "lb" in value ? value["lb"] : utils.capitalize(view);
        $("#jq-table").append($("<option>", { value: view }).html(viewLib));
      });

    });

}

function loadExample(exampleDiv) {
  $(".nav-item").removeClass("active")
  exampleDiv.addClass("active")
  console.log("loadExample")
  $(".definition-display .example-title").html(exampleDiv.html());
  $(".definition-toggle").hide();
  $(".examples-sidebar").hide();
  $(".examples-numbers-container").empty();
  $(".syntax-block").hide();
  let view = exampleDiv.attr("data-view"),
    columns = exampleDiv.attr("data-column"),
    filters = exampleDiv.attr("data-filter"),
    next = exampleDiv.attr("data-next"),
    prev = exampleDiv.attr("data-prev");

  $(".tuto_btn_container").removeClass("visible");
  if (next) {
    $(".tuto_btn_container.btn-next").addClass("visible");
    $(".tuto_btn_container.btn-next span:first").html(next);
    $(".tuto_btn_container.btn-next").attr(
      "data-example",
      next.toLowerCase()
    );
  }
  if (prev) {
    $(".tuto_btn_container.btn-prev").addClass("visible");
    $(".tuto_btn_container.btn-prev span:first").html(prev);
    $(".tuto_btn_container.btn-prev").attr(
      "data-example",
      prev.toLowerCase()
    );
  }

  if (exampleDiv.attr("data-tutorial")) {
    loadTutorial(exampleDiv.attr("data-tutorial"));
    if (view || columns || filters) {
      $(".definition-element.try_it").show();
    }
  } else {
    $(".definition-element").show();
    $(".definition-toggle").show();
    $(".definition-element.tutorial").hide();
    const definition = exampleDiv.attr("data-definition"),
      syntax = exampleDiv.attr("data-syntax");
    const defToolTip = $(".definition-toggle")[0]._tippy;
    if (defToolTip) {
      defToolTip.setContent(definition);
    } else {
      tippy(".definition-toggle", {
        content: definition,
        animation: 'scale',
        arrow: true,
      });
    }
    if (syntax) {
      $(".syntax-block.url").empty();
      const syntaxes = syntax.split("&;");
      console.log("syntaxes : ", syntaxes)
      $.each(syntaxes, (key, val) => {
        console.log("syntax val : ", val)
        $(".syntax-block.url").append(
          $("<pre>").append($("<code>", { class: "syntax-code language-scss" }).html(val))
        )
      })
      $(".syntax-block.url").show();
      // $(".syntax-code").html(syntax);
    }

    if (exampleDiv.attr("data-java")) {
      utils.loadMarkDown("/tutorials/java/" + exampleDiv.attr("data-java"), $(".syntax-block.java")).then(() => {
        $(".syntax-block.java").show();
      });
    }
    const examples = exampleDiv.attr("data-examples") ? JSON.parse(exampleDiv.attr("data-examples")) : [];
    if (view || columns || filters) {
      examples.unshift({ "title": "BASIC " + exampleDiv.html(), "view": view ?? "", "filter": filters ?? "", "column": columns ?? "" })
    }

    if (examples.length > 1) {
      $(".examples-sidebar").show();
    }

    $.each(examples, (key, val) => {
      const index = key + 1,
        example = $("<div>", { class: "example-number" }).html(index);
      example.on('click', () => {
        $("#jq-table").val(val.view ?? "");
        $("#jq-columns").val(val.column ?? "");
        $("#jq-filters").val(val.filter ?? "");
        $(".example-number").removeClass("active");
        example.addClass("active");
        fetchJQData();
      });

      tippy(example[0], {
        content: val.title ?? "Example " + index,
        animation: 'scale',
        arrow: true,
        placement: 'right'
      });
      $(".examples-numbers-container").append(example)
    })
    hljs.highlightAll();
  }
  $(".content").animate({ scrollTop: 0 }, 10);
  $('.syntax-content').hide();
  introNextStep(() => $('.syntax-content').show());
}

function fetchJQData() {
  let table = $("#jq-table").val() ? $("#jq-table").val() : null;
  let columns = $("#jq-columns").val();
  let filters = $("#jq-filters").val();
  let fetchLink =
    "/" +
    table + "?" +
    (columns ? "select=" + columns : "") +
    (filters ? "&" + filters : "");
  showLoading($(".loader"), $(".query-status-btn"));
  if (table) {
    $(".jq-link-display").attr("href", window.location.origin + fetchLink);
    $(".jq-link-display").html(fetchLink);
    $(".jq-link-display").css("visibility", "visible");
    console.log("link to fetch : ", fetchLink);
    fetch(fetchLink)
      .then((response) => response.json())
      .then((data) => {
        setTimeout(() => {
          showSuccess("Query executed successfully : " + data.result.length + " rows.");
          hideLoading($(".loader"), $(".query-status-btn"))
        }, 300)
        // console.log("response : ",response);
        console.log("data : ", data);
        $(".error_container").hide();
        $("#sql-code").html(sqlFormatter.format(data.query));
        $("#sql-display").show();

        hljs.highlightAll();
        if (data.result.length > 0) {
          displayTableResults(data.result);
        } else {
          clearTable();
        }
      })
      .catch((error) => {
        console.log("error : ", error)
        let errorMessage = "Error while executing this query.";
        clearTable();
        $("#sql-display").hide();
        showError(errorMessage);
        // $(".error_container").show();
        // $("#error-code").html(errorMessage);
        // console.error("Error fetching data: ", error);
      });
  }
}

function loadTutorial(fileName) {
  console.log("loading Tutorial with the file : ", fileName);
  $(".definition-element").hide();
  $(".definition-element.tutorial").show();
  utils.loadMarkDown("tutorials/" + fileName, $(".highlighted_code"));
}
