export function checkForFile(path) {
  return new Promise((res, rej) => {
    $.get(path)
      .done(function (data) {
        console.log("FOUND FILE : ", path)
        res(data)
      })
      .fail(function (jqXHR, textStatus, errorThrown) {
        rej(new Error(`File NOT FOUND ${textStatus}`));
      });
  })
}

export function loadMarkDown(path, div) {
  return checkForFile(path).then((data) => {
    var htmlContent = marked.parse(data);
    console.log("htmlcontent : ", htmlContent);
    // Insert the HTML into the div
    div.html(htmlContent);

    // Highlight all code blocks after inserting the HTML
    hljs.highlightAll();
    return;
  })
}

export function capitalize(str) {
  return str[0].toUpperCase() + str.slice(1).toLowerCase();
}
// remove second class from div
export function removeSecondClass(element) {
  var classList = element.attr("class").split(/\s+/);
  if (classList.length > 1) {
    var secondClass = classList[1]; // Get the second class
    element.removeClass(secondClass); // Remove the second class
  }
}

// Swap divs positions
export function swapDivsOnce(swapBtnElement, parentSelector = ".settings-content") {
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
export function displayDivAtCursor(divElement, e) {
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
export function placeAtTop(divElement = $(".form-container")) {
  var parentDiv = divElement.parent();
  console.log("div to move at Top : ", divElement);
  console.log("Parent div : ", parentDiv);
  divElement.prependTo(parentDiv);
}

$(".btn-trigger").on("click", (e) => {
  let elementToHide = $(e.currentTarget).attr("hide"),
    elementToShow = $(e.currentTarget).attr("show"),
    visibleElements = $(e.currentTarget).attr("visible"),
    invisibleElements = $(e.currentTarget).attr("invisible"),
    elementAtCursor = $(e.currentTarget).attr("cursor-data");
  if (visibleElements) {
    $.each(visibleElements.split(","), (key, element) => {
      $(element).css("visibility", "visible");
    });
  }
  if (invisibleElements) {
    $.each(invisibleElements.split(","), (key, element) => {
      $(element).css("visibility", "hidden");
    });
  }
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