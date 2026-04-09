var resultTable;
export function displayTableResults(data) {
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
    // shouldUpdate = false;
}

export function clearTable() {
    if ($.fn.dataTable.isDataTable(".results-container table"))
        resultTable.destroy();
    $(".results-container table").empty();
    $(".results-container").hide();
    $(".results-container").css("width", "0");
}

export function showTable() {
    $(".results-container").show();
    $(".results-container").css("width", "100%");
}