/**
 * Created by cufa-03 on 1/10/15.
 */
function setUpReportGrid() {
    jQuery("#reportGrid").jqGrid(
        {
            jsonreader: {
                root: "rows",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: true,
                cell: "cell",
                id: "id"
            },
            gridview: false,
            datatype: "json",
            mtype: 'GET',
            url: 'viewreport.action',
            colModel: [
                {
                    name: 'id',
                    index: 'id',
                    hidden: true
                },
                {
                    label: 'First Name',
                    name: 'firstName',
                    index: 'firstName',
                    editable: true,
                    sortable: true,
                    hidden: false,
                    align: 'right'
                },
                {
                    label: 'Email',
                    name: 'email',
                    index: 'email',
                    editable: true,
                    sortable: true,
                    hidden: false,
                    align: 'right'
                }

            ],
            rowNum: 5,
            rowList: [5, 10, 15, 20],
            pager: '#reportGridPager',
            height: 'auto',
            //width: 600,
            autowidth: true,
            // shrinkToFit: true,
            forceFit: true,
            multipleSearch: true,
            viewrecords: true,
            emptyrecords: "No Data",
            sortname: 'email',
            sortorder: 'asc',
            caption: 'Manage Parent',
            subGrid: true,
            subGridRowExpanded: function (subgrid_id, row_id) {
                var subgrid_table_id = subgrid_id + "_t";
                var subgrid_table_pager_id = subgrid_id + "_pt";
                jQuery("#" + subgrid_id)
                    .html(
                    "<table id='" + subgrid_table_id + "' class='scroll'></table>\n<DIV id='" + subgrid_table_pager_id
                    + "'></DIV>");

                var selectedRow = jQuery(this).getRowData(row_id);
                var subQuery = "parentId=" + selectedRow['id'];

                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url: "viewchildreport.action?" + subQuery,
                        datatype: "json",
                        mtype: 'GET',
                        hidegrid: false,
                        colModel: [
                            {
                                name: 'id',
                                index: 'id',
                                hidden: true
                            },
                            {
                                label: 'First Name',
                                name: 'firstName',
                                index: 'firstName',
                                editable: true,
                                sortable: true,
                                hidden: false,
                                align: 'right'
                            },
                            {
                                label: 'Last Name',
                                name: 'lastName',
                                index: 'lastName',
                                editable: true,
                                sortable: true,
                                hidden: false,
                                align: 'right'
                            }

                        ],
                        height: 'auto',
                        rowNum: 20,
                        autowidth: true,
                        forceFit: true,
                        rowList: [10, 20, 30, 50],
                        viewrecords: true,
                        emptyrecords: "No Data",
                        sortname: 'firstName',
                        sortorder: 'desc',
                        caption: '',
                        pager: '#' + subgrid_table_pager_id,

                        loadComplete: function () {
                        },
                        onCellSelect: function (row, col, value, e) {
                        }
                    });

                //code for highlighting the selected one
                if (typeof (subgridSelectCell) !== "undefined") {
                    subgridSelectCell.removeClass('ui-state-highlight');
                    subgridSelectCell.next('tr').find('td.subgrid-cell').removeClass('ui-state-highlight');
                }
                jQuery('#' + row_id).addClass('ui-state-highlight');
                jQuery('#' + row_id).next('tr').find('td.subgrid-cell').addClass('ui-state-highlight');
            }
        })

}