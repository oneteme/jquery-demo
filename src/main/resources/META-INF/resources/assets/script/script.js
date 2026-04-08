var intro,
  introIsCompleted = true,
  navStyleFields = {},
  viewsMap = {},
  resultTable,
  shouldUpdate,
  inputTimeOut;
//**************** EVENT LISTENERS ****************/
$(document).ready(function () {

  loadNavbar();
  loadSettings();
  loadViews();
  $("#jq-live").on("click", (e) => {
    setupIntro();
  });
  $("#jq-execute").on("click", (e) => {
    if (!introIsCompleted) {
      intro.nextStep();
    }
    // $("#query-form").submit();
  });
  $("#query-form").on("submit", (e) => {
    e.preventDefault(); // Prevent the default form submission
    fetchJQData();
  });
  $("#jq-table").on("change", (e) => {
    $("#jq-columns").val("");
    $("#jq-filters").val("");

    fetchJQData();
    if (!introIsCompleted && $("#jq-table").val() === "customers")
      setTimeout(function () {
        intro.nextStep();
      }, 500);
  });
  $(document).on("click", ".jq-column", (e) => {
    let columnsText = $("#jq-columns").val().split(","),
      chosenColumn = $(e.currentTarget).html();
    if (columnsText[columnsText.length - 1] == "") {
      columnsText[columnsText.length - 1] = chosenColumn
    } else {
      columnsText.push(chosenColumn)
    }
    $("#jq-columns").val(columnsText.join(","))
  })
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
  $(document).on(
    "click",
    ".navbar-container .parent_title[isloaded='false']",
    (e) => {
      console.log("clicked on a unloaded menu");
      let subMenuFile = $(e.currentTarget).attr("sub-menu"),
        title = $(e.currentTarget).find("span:first").html();
      fetch("subMenu/" + subMenuFile)
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          createNavbar(data, $(".sub-nav[data-title='" + title + "']"));
          $(e.currentTarget).attr("isloaded", true);
          toggleNavSubElements(e);
        });
    }
  );
  $(document).on("click", ".jq-example", (e) => {
    $(".content.definition-display").show();
    $(".content.jquery-display").hide();
    loadExample($(e.currentTarget));
  });
  $(document).on("click", ".grid-options .option-box", (e) => {
    let appliedClass = $(e.currentTarget).attr("data-class");
    removeSecondClass(".settings-content");
    $(".settings-content").addClass(appliedClass);
  });

  $(".position-options .option-box").on("click", (e) => {
    swapDivsOnce($(e.currentTarget));
  });

  $("#jq-columns").on("input", (e) => {
    clearTimeout(inputTimeOut);
    if (
      !introIsCompleted &&
      $(e.currentTarget).val() === "country,name,contact,id"
    ) {
      inputTimeOut = setTimeout(() => {
        intro.nextStep();
      }, 800);
    }
  });
  $("#jq-filters").on("input", (e) => {
    clearTimeout(inputTimeOut);
    if (!introIsCompleted && $(e.currentTarget).val() === "country=France") {
      inputTimeOut = setTimeout(() => {
        intro.nextStep();
      }, 800);
    }
  });
  $(".jqsyntax-container input").on("blur", (e) => {
    if (introIsCompleted) $("#query-form").submit();
  });
  $(".show-docs").on("click", (e) => {
    introNextStep();
  });
  $(".close_window_btn").on("click", (e) => {
    $(e.currentTarget).parent().hide();
  });
  $(".btn-trigger").on("click", (e) => {
    let elementToHide = $(e.currentTarget).attr("hide"),
      elementToShow = $(e.currentTarget).attr("show");
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
        displayDivAtCursor($(element), e);
      });
  });
  $(".definition-element .show-demo").on("click", (e) => {
    introNextStep();
  });
  $(".tuto_btn_container").on("click", (e) => {
    let example = $(e.currentTarget).attr("data-example");
    $(".jq-example[data-learn='" + example + "']").click();
  });
});
//**************** FUNCTIONS ****************/
function introNextStep(fn = null) {
  if (!introIsCompleted) {
    setTimeout(function () {
      intro.nextStep();
    }, 500);
    if (fn) {
      fn()
    }
  }
}

function setupIntro() {
  // *** DIVS TO SHOW/HIDE FOR TUTORIAL
  $(".jq-link-display").hide();
  $("#sql-display").hide();
  clearTable();
  $(".definition-container").hide();
  $("#jq-columns").val("");
  $("#jq-filters").val("");
  $(".content").hide();
  $(".jquery-display").show();
  hideNavBar();
  placeAtTop();
  // *** DIVS TO SHOW/HIDE FOR TUTORIAL

  introIsCompleted = false;
  intro = introJs();
  intro
    .onbeforeexit(function () {
      if (confirm("Are you sure ?")) {
        introIsCompleted = true;
        return true;
      }
      return false;
    })
    .setOptions({
      showProgress: true,
      showBullets: false,
      steps: [
        {
          title: "Welcome to JQuery Demo👋",
          intro:
            "Explore Jarvis's Open Source jQuery library to discover its functions with examples. You can also create your own queries to test it out!",
        }, // step 0
        {
          disableInteraction: true,
          title: "Table selection",
          element: ".table_input_container",
          intro: "This is where you choose the table which you want to test",
        }, // step 1
        {
          disableInteraction: true,
          title: "What's a Table ?",
          element: ".table_input_container select",
          intro:
            "it's a structured collection of data organized into rows and columns, used to store and manage information in a database. \nFor instance, in a table named 'Customers', you might find all the data related to a customer (name, contact, and address).",
        }, // step 2
        {
          title: "What's a Table ?",
          element: ".table_input_container select",
          intro: "Choose <b>Customers</b> to give it a try",
        }, // step 3
        {
          title: "Result display",
          element: ".results-container",
          intro: "Your query result will be here",
        }, // step 4
        {
          title: "Rows",
          element: ".results-container",
          intro: "These are all the customers we have in the database",
        }, // step 5
        {
          title: "Columns",
          element: ".results-container",
          intro:
            "As you can see we fetched all <b>columns</b> which are : <b>id, name(customer), contact, address, city, postal_code and country</b>. But what if we need to get only some specific columns?",
        }, // step 6
        {
          disableInteraction: true,
          title: "JQuery syntax",
          element: ".jqcolumns_input_container",
          intro:
            "In here you can put all the columns and functions you want to use for your query (Example : if you want to fetch the customer name,contact and id then your JQuery syntax should be <b>customer,contact,id</b>. Give it a shot!)",
        }, // step 7
        {
          title: "JQuery syntax",
          element: ".jqcolumns_input_container input",
          intro: "Write : <b>country,name,contact,id</b>",
        }, // step 8
        {
          title: "JQuery syntax",
          element: ".jqfilters_input_container input",
          intro: "To apply a filter<br>Write : <b>country=France</b>",
        }, // step 9
        {
          scrollToElement: false,
          title: "Execute query",
          element: "#jq-execute",
          intro: "Execute!",
        }, // step 10
        {
          scrollToElement: false,
          title: "Filter applied 👌👌",
          element: ".results-container",
          intro:
            "And now here is your new query result from getting all customers who are in France but and we only fetched country,name,contact and id",
        }, // step 11
        {
          title: "SQL Query",
          element: "#sql-display",
          intro:
            "And by the way, This how your query looks like when using SQL (you can compare JQuery and SQL in realtime!!)",
        }, // step 12
        {
          title: "JQuery Examples",
          element: "#jq-show-examples",
          intro: "Click here to show JQuery examples 😃",
        }, // step 13
        {
          disableInteraction: true,
          title: "JQuery Examples",
          element: ".navbar-container",
          intro:
            "In here you can find all different examples for all JQuery functions so you find out more about how it works 😃",
        }, // step 14
        {
          title: "JQuery Examples",
          element: ".jq-example[data-learn='select']",
          intro: 'Choose the "SELECT" Example for starters',
        }, // step 15
        {
          disableInteraction: true,
          title: "Explanation 🤓",
          element: ".definition-element.definition",
          intro: "Here is the definition of the example which you chose",
        }, // step 16
        {
          disableInteraction: true,
          title: "Explanation 🤓",
          element: ".syntax-content",
          intro: "And this is how it's syntax works in JQuery",
        }, // step 17
        {
          title: "Try it for yourself 🤓",
          element: ".definition-element .show-demo",
          intro: "Click here to see your example live ",
        }, // step 18
        {
          disableInteraction: true,
          title: "Example tryout",
          element: ".form-content",
          intro:
            "The view,columns and filters have been automatically filled with an example to show you how your example works in JQuery",
        }, // step 19
        {
          title: "Docs 📖",
          element: ".show-docs",
          intro: "You can click here to go back to the definition page",
        }, // step 20
        {
          title: "The end 👏👏",
          intro: "And the JQueryDemo tutorial ends here. Enjoy!",
        }, // step 21
      ],
    })
    .start();
  // Disable the next button in Intro.js
  intro.onbeforechange(function (targetElement) {
    var currentStep = intro._introItems[intro._currentStep],
      hideNextBtnSteps = [3, 8, 9, 10, 15, 13, 18];
    console.log("intro init onbeforechange");
    console.log("currentstep : ", currentStep);
    console.log("currentstep num : ", intro._currentStep);
    console.log("currentstep element : ", currentStep.element);
    // Check if we are on the step with the select element
    if (hideNextBtnSteps.includes(intro._currentStep)) {
      // Disable the next button
      $(".introjs-nextbutton").hide();
      if (intro._currentStep === 3) {
        $("#jq-table").val("");
      }
    } else {
      // Ensure the next button is enabled for other steps
      $(".introjs-nextbutton").show();
    }
    // $('.content').scrollTop(0);
  });

  intro.oncomplete(function () {
    introIsCompleted = true;
  });
}

function loadSettings() {
  fetch("/grid_settings.json")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
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

function showNavBar(element = $("#jq-show-examples")) {
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

function hideNavBar(element = $("#jq-show-examples")) {
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

function createNavbar(data, element = $(".navbar-nav")) {
  console.log("createNavBar => data : ", data);
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
        arrow: true,
        arrowType: 'round',
        size: 'large',
      });
    }
  });
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

function loadViews() {
  fetch("/views.json")
    .then((response) => response.json())
    .then((data) => {
      $.each(data, (key, value) => {
        let view = value["view"],
          viewLib = "lb" in value ? value["lb"] : capitalize(view);
        $("#jq-table").append($("<option>", { value: view }).html(viewLib));
        fetch("/" + view + "?limit=1")
          .then((response) => response.json())
          .then((data) => {
            let columns = Object.keys(data.result[0]);
            viewsMap[view] = columns
          });
      });

    });

}

function loadNavbar() {
  $(".navbar-nav").empty();
  fetch("/menu.json")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      createNavbar(data);
    })
}

function setupNavItem(navItem, divElement, next, prev) {
  let title = navItem.title,
    navFields = {
      class: "nav-item jq-example",
      "data-learn": title.toLowerCase(),
      "data-next": next,
      "data-prev": prev,
    };
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
  $(divElement).append($("<li>", navFields).html(title));
}

function toggleNavSubElements(e, subElement = ".sub-nav") {
  if ($(e.currentTarget).find(".accordion").hasClass("rot-accordion")) {
    $(e.currentTarget).find(".accordion").removeClass("rot-accordion");
    $(e.currentTarget).siblings(subElement).hide(".sub-nav");
  } else {
    $(e.currentTarget).find(".accordion").addClass("rot-accordion");
    $(e.currentTarget).siblings(subElement).show();
  }
}

function toggleNavBar(animationTime = 100) {
  $(".navbar-container").animate(navStyleFields, animationTime, () => {
    $(".navbar-container").css("display", navStyleFields.display);
  }); // duration in milliseconds
}

function loadExample(exampleDiv) {
  console.log("loadExample")
  $(".definition-display .example-title").html(exampleDiv.html());
  $(".definition-toggle").hide();
  $(".examples-sidebar").hide();
  $(".examples-numbers-container").empty();
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
    $(".definition-element.move_tuto").hide();
    const definition = exampleDiv.attr("data-definition"),
      syntax = exampleDiv.attr("data-syntax");
    console.log("view : ", view);
    console.log("columns : ", columns);
    console.log("[EXAMPLE]", "definition : ", definition)
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
    $("#defintion-text-container p").html(definition);
    $("#syntax-text-container p").html(syntax);
    $(".definition-description-content p").html(definition);
    $(".syntax-code").html(syntax);
    if (exampleDiv.attr("data-examples")) {
      const examples = JSON.parse(exampleDiv.attr("data-examples"));
      if (examples.length > 1) {
        $(".examples-sidebar").show();
        $.each(examples, (key, val) => {
          const example = $("<div>", { class: "example-number" }).html(key + 1);
          example.on('click', () => {
            console.log("clicked on example : ", val.title);
          });

          tippy(example[0], {
            content: val.title,
            animation: 'scale',
            arrow: true,
            placement: 'right'
          });
          $(".examples-numbers-container").append(example)
        })
      }
    }
  }

  $(".content").animate({ scrollTop: 0 }, 10);
  $("#jq-table").val(view);
  $("#jq-columns").val(columns);
  $("#jq-filters").val(filters);
  fetchJQData();
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
  if (table) {
    console.log(viewsMap)
    console.log(viewsMap[table])
    $(".select-columns").empty()
    $.each(viewsMap[table], function (index, column) {
      $(".select-columns").append(
        $("<div>", { class: "jq-column" }).html(column)
      );
    })
    console.log("current url : ", window.location.origin)
    $("#jquery-link").closest('a').attr("href", window.location.origin + fetchLink);
    $("#jquery-link").html(fetchLink);
    $(".jq-link-display").show();
    console.log("link to fetch : ", fetchLink);
    fetch(fetchLink)
      .then((response) => response.json())
      .then((data) => {
        // console.log("response : ",response);
        console.log("data : ", data);
        $(".error_container").hide();
        $("#sql-code").html(sqlFormatter.format(data.query));
        $("#sql-display").show();

        hljs.highlightAll();
        displayTableResults(data.result);
      })
      .catch((error) => {
        let errorMessage = "Error while executing this query.";
        clearTable();
        $("#sql-display").hide();
        $(".error_container").show();
        $("#error-code").html(errorMessage);
        // console.error("Error fetching data: ", error);
      });
  }
}

function displayTableResults(data) {
  clearTable();
  showTable();
  $(".results-container").css("width", "100%");
  $(".jq-params").show();
  let tableContainer = $(".results-container table");
  var columnsHeader = Object.keys(data[0]);
  console.log(columnsHeader);

  // Create table header row
  let headerRow = $("<tr>", { class: "table_header" });
  $.each(columnsHeader, function (index, column) {
    headerRow.append($("<th>").text(column));

  });
  tableContainer.append($("<thead>").append(headerRow));
  tableContainer.append($("<tbody>"));
  // Create table rows with data
  $.each(data, function (index, rowData) {
    let row = $("<tr>", { class: "table_row" });
    $.each(rowData, function (key, value) {
      row.append($("<td>").text(value));
    });
    tableContainer.find("tbody").append(row);
  });

  resultTable = tableContainer.DataTable({
    autoWidth: false,
    ordering: false,
    lengthMenu: [5, 10, 15, 20, 25]
  });
  shouldUpdate = false;
}

function clearTable() {
  if ($.fn.dataTable.isDataTable(".results-container table"))
    resultTable.destroy();
  $(".results-container table").empty();
  $(".results-container").hide();
  $(".results-container").css("width", "0");
}

function showTable() {
  $(".results-container").show();
  $(".results-container").css("width", "100%");
}

function loadTutorial(fileName) {
  console.log("loading Tutorial with the file : ", fileName);
  $(".definition-element").hide();
  $(".definition-element.tutorial").show();
  loadMarkDown("tutorials/" + fileName, ".highlighted_code");
}

function loadMarkDown(path, div) {
  $.get(path, function (data) {
    // console.log("markdown data : ", data);
    // Convert Markdown to HTML
    var htmlContent = marked.parse(data);
    console.log("htmlcontent : ", htmlContent);
    // Insert the HTML into the div
    $(div).html(htmlContent);

    // Highlight all code blocks after inserting the HTML
    hljs.highlightAll();
  });
}
function capitalize(str) {
  return str[0].toUpperCase() + str.slice(1).toLowerCase();
}
// remove second class from div
function removeSecondClass(element) {
  var classList = $(element).attr("class").split(/\s+/);
  if (classList.length > 1) {
    var secondClass = classList[1]; // Get the second class
    $(element).removeClass(secondClass); // Remove the second class
  }
}

// Swap divs positions
function swapDivsOnce(swapBtnElement, parentSelector = ".settings-content") {
  var parent = $(parentSelector);

  var divs = parent.children("div:visible");
  if (divs.length > 1) {
    var swapStatus = swapBtnElement.attr("swap-status"), // swap right or left (1,0)
      insertWay = swapStatus == "1" ? "insertBefore" : "insertAfter", // insert before or after the element
      movedDivIndex = swapStatus == "1" ? divs.length - 1 : 0,
      movedToDivIndex = swapStatus == "1" ? 0 : divs.length - 1, // The div which we go before or after
      movedDiv = $(divs[movedDivIndex]);
    movedDiv[insertWay]($(divs[movedToDivIndex]));
  }
}

// Show div at cursor's position
function displayDivAtCursor(divElement, e) {
  divElement.hide();
  var screenWidth = $(window).width();
  var screenHeight = $(window).height();
  var containerWidth = divElement.outerWidth();
  var containerHeight = divElement.outerHeight();
  var leftPosition = e.pageX;
  var topPosition = e.pageY;
  // Check if div overflows with the screen borders
  if (leftPosition + containerWidth > screenWidth) {
    leftPosition = Math.max(e.pageX - containerWidth, 0);
  }
  if (topPosition + containerHeight > screenHeight) {
    topPosition = Math.max(e.pageY - containerHeight, 0);
  }
  divElement
    .css({
      position: "absolute",
      left: leftPosition,
      top: topPosition,
    })
    .fadeIn(350);
}

// Place div at top
function placeAtTop(divElement = $(".form-container")) {
  var parentDiv = divElement.parent();
  console.log("div to move at Top : ", divElement);
  console.log("Parent div : ", parentDiv);
  divElement.prependTo(parentDiv);
}
