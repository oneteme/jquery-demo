import { hideNavBar } from "./navbar.js";
import { clearTable } from "./table.js";
import * as utils from "./utils.js";

const introSteps = [
    {
        title: "Welcome to JQuery Demo👋",
        intro:
            "Explore Jarvis's Open Source jQuery library to discover its functions with examples. You can also create your own queries to test it out!",
    }, // step 0
    {
        disableInteraction: true,
        title: "Table selection",
        element: "#jq-table",
        intro: "This is where you choose the table which you want to test",
    }, // step 1
    {
        disableInteraction: true,
        title: "What's a Table ?",
        element: "#jq-table",
        intro: "it's a structured collection of data organized into rows and columns, used to store and manage information in a database. \nFor instance, in a table named 'Customers', you might find all the data related to a customer (name, contact, and address).",
    }, // step 2
    {
        title: "What's a Table ?",
        element: "#jq-table",
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
        intro: "As you can see we fetched all <b>columns</b> which are : <b>id, name(customer), contact, address, city, postal_code and country</b>. But what if we need to get only some specific columns?",
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
        element: ".jq-example[data-learn='case']",
        intro: 'Choose the "CASE" Example for starters',
    }, // step 15
    {
        disableInteraction: true,
        title: "URL syntax",
        element: ".syntax-block.url",
        intro: "This is how to use the \"CASE WHEN\" on the URL",
    }, // step 16
    {
        disableInteraction: true,
        title: "JAVA syntax",
        element: ".syntax-block.java",
        intro: "This is how to use the \"CASE WHEN \" in JAVA (backend)",
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
        disableInteraction: true,
        title: "Multiple examples",
        element: ".examples-numbers-container",
        intro: "Some functions may have multiple use cases that you can check in here",
    }, // step 20
    {
        title: "Multiple examples",
        element: ".examples-numbers-container > .example-number:nth-child(2)",
        intro: "Click on the second example to see how to call the JAVA \"CASE\" on the URL",
    }, // step 21
    {
        title: "Multiple examples",
        element: ".form-content",
        intro: "And here is the second use case 😉",
    }, // step 22
    {
        title: "Docs 📖",
        element: ".show-docs",
        intro: "You can click here to go back to the definition page",
    }, // step 23
    {
        title: "The end 👏👏",
        intro: "And the JQueryDemo tutorial ends here. Enjoy!",
    }, // step 24
];
var intro,
    introIsCompleted = true;
export function isIntro() {
    return !introIsCompleted;
}

export function introNextStep(timeOut = 500, fn = null) {
    introNextStepCondition(true, timeOut, fn);
}

export function introNextStepCondition(condition, timeOut = 500, fn = null) {
    if (isIntro() && condition) {
        setTimeout(function () {
            intro.nextStep();
        }, timeOut);
        if (fn) {
            fn()
        }
    }
}

function setupIntro() {
    // *** DIVS TO SHOW/HIDE FOR TUTORIAL
    $(".jq-link-display").css("visibility", "hidden");
    $("#sql-display").hide();
    clearTable();
    $(".definition-container").hide();
    $("#jq-columns").val("");
    $("#jq-filters").val("");
    $(".content").hide();
    $(".jquery-display").show();
    hideNavBar();
    utils.placeAtTop();
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
            steps: introSteps,
        })
        .start();
    // Disable the next button in Intro.js
    intro.onbeforechange(function (targetElement) {
        var currentStep = intro._introItems[intro._currentStep],
            hideNextBtnSteps = [3, 8, 9, 10, 15, 13, 18, 21];




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

$("#jq-live").on("click", (e) => {
    setupIntro();
});