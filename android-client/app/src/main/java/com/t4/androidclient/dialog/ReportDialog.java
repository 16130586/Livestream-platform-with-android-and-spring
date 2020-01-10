//package com.t4.androidclient.dialog;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.widget.ListAdapter;
//
//import androidx.fragment.app.DialogFragment;
//
//import com.t4.androidclient.R;
//import com.t4.androidclient.model.report.ReportReason;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReportDialog extends DialogFragment {
//    private List<ReportReason> reportReasons;
//
//    @Override
//    public ReportDialog onCreateDialog(Bundle savedInstanceState) {
//        getReasonList();
//        ReportReason selectedReason; // Where we track the selected items
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Set the dialog title
//        builder.setTitle("Report")
//                // Specify the list array, the items to be selected by default (null for none),
//                // and the listener through which to receive callbacks when items are selected
//                .setSingleChoiceItems(getResources().getStringArray(R.array.report_reason), 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                // Set the action buttons
//                .setPositiveButton("Report", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User clicked OK, so save the selectedItems results somewhere
//                        // or return them to the component that opened the dialog
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
//
//        return builder.create();
//    }
//
//    public void getReasonList() {
//        reportReasons = new ArrayList();
//
//    }
//}
