package com.example.printmaster.fragment.homefragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.printmaster.Compatible_List_Activity;
import com.example.printmaster.ConnectDeviceActivity;
import com.example.printmaster.Lable_Intro_Activity;
import com.example.printmaster.R;
import com.example.printmaster.SelectLayoutActivity;
import com.example.printmaster.SelectOptionActivity;
import com.example.printmaster.WebViewActivity;
import com.example.printmaster.adapter.ImagePrintDocumentAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    LinearLayout seeMoreLinear;
    TextView connectTv;
    RelativeLayout photoRel, emailRel, documentRel, webPageRel, labelRel;
    private static final int REQUEST_CODE = 42;
    Uri uri = null;
    private boolean isItemSelected = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences_device = getActivity().getSharedPreferences("info_device", Context.MODE_PRIVATE);
        String nameDevice = sharedPreferences_device.getString("name", ""); // Đọc chuỗi
        if (nameDevice.length() > 17) {
            nameDevice = nameDevice.substring(0, Math.min(nameDevice.length(), 14)) + "...";
        } else {
            if (nameDevice.isEmpty()) {
                nameDevice = "Connect a printer";
            }
        }
        Log.d("QuocDat", "onCreateView: " + nameDevice);

        seeMoreLinear = view.findViewById(R.id.seeMoreLinear);
        connectTv = view.findViewById(R.id.connectTv);
        photoRel = view.findViewById(R.id.photoRel);
        emailRel = view.findViewById(R.id.emailRel);
        documentRel = view.findViewById(R.id.documentRel);
        webPageRel = view.findViewById(R.id.webPageRel);
        labelRel = view.findViewById(R.id.labelRel);
        connectTv.setText(nameDevice);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }

        connectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isItemSelected) {
                    isItemSelected = true;
                    Intent intent = new Intent(getContext(), ConnectDeviceActivity.class);
                    startActivity(intent);
                }
            }
        });
        seeMoreLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isItemSelected) {
                    isItemSelected = true;
                    Intent intent = new Intent(getContext(), Compatible_List_Activity.class);
                    startActivity(intent);
                    isItemSelected = false;
                }
            }
        });
        photoRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isItemSelected) {
                    isItemSelected = true;
                    View dialogView = getLayoutInflater().inflate(R.layout.action_sheet_home_layout, null);
                    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                    dialog.setContentView(dialogView);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // Xử lý khi dialog được đóng ở đây
                            isItemSelected = false; // Đặt lại trạng thái của isItemSelected
                        }
                    });
                    dialog.show();
                    RelativeLayout quickRel = dialogView.findViewById(R.id.quickRel);
                    RelativeLayout sheetRel = dialogView.findViewById(R.id.sheetRel);
                    RelativeLayout posterRel = dialogView.findViewById(R.id.posterRel);
                    RelativeLayout cancelRel = dialogView.findViewById(R.id.cancelRel);
                    quickRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), QuickActivity.class);
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("activity", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("activity", "photo");
                            editor.apply();
                            startActivity(intent);
                        }
                    });
                    sheetRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), SelectOptionActivity.class);
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("activity", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("activity", "sheet");
                            editor.apply();
                            startActivity(intent);
                        }
                    });
                    posterRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), SelectOptionActivity.class);
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("activity", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("activity", "post");
                            editor.apply();
                            startActivity(intent);
                        }
                    });
                    cancelRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            isItemSelected = false;
                        }
                    });
                }
            }
        });
        emailRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isItemSelected) {
                    isItemSelected = true;
                    View dialogView = getLayoutInflater().inflate(R.layout.action_sheet_email, null);
                    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                    dialog.setContentView(dialogView);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // Xử lý khi dialog được đóng ở đây
                            isItemSelected = false; // Đặt lại trạng thái của isItemSelected
                        }
                    });
                    dialog.show();
                    RelativeLayout mailRel = dialogView.findViewById(R.id.emailRel);
                    RelativeLayout icloudRel = dialogView.findViewById(R.id.icloudRel);
                    RelativeLayout outlookRel = dialogView.findViewById(R.id.outlookRel);
                    RelativeLayout zohoRel = dialogView.findViewById(R.id.zohoRel);
                    RelativeLayout otherRel = dialogView.findViewById(R.id.otherRel);
                    RelativeLayout cancelRel = dialogView.findViewById(R.id.cancelRel);
                    icloudRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            startActivity(intent);
                        }
                    });
                    mailRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            startActivity(intent);
                        }
                    });
                    icloudRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            startActivity(intent);
                        }
                    });
                    outlookRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            startActivity(intent);
                        }
                    });
                    zohoRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            startActivity(intent);
                        }
                    });
                    otherRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            startActivity(intent);
                        }
                    });
                    cancelRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            isItemSelected = false;
                        }
                    });
                }

            }
        });
        documentRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isItemSelected) {
                    isItemSelected = true;
                    View dialogView = getLayoutInflater().inflate(R.layout.action_sheet_document, null);
                    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                    dialog.setContentView(dialogView);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // Xử lý khi dialog được đóng ở đây
                            isItemSelected = false; // Đặt lại trạng thái của isItemSelected
                        }
                    });
                    dialog.show();
                    RelativeLayout icloudRel = dialogView.findViewById(R.id.icloudRel);
                    RelativeLayout dropboxRel = dialogView.findViewById(R.id.dropboxRel);
                    RelativeLayout oneRel = dialogView.findViewById(R.id.oneRel);
                    RelativeLayout googleRel = dialogView.findViewById(R.id.googleDriveRel);
                    RelativeLayout otherRel = dialogView.findViewById(R.id.otherRel);
                    RelativeLayout cancelRel = dialogView.findViewById(R.id.cancelRel);
                    icloudRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("*/*"); // Chọn tất cả loại tập tin, thay đổi theo nhu cầu của bạn
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    });
                    dropboxRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("*/*"); // Chọn tất cả loại tập tin, thay đổi theo nhu cầu của bạn
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    });
                    oneRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("*/*"); // Chọn tất cả loại tập tin, thay đổi theo nhu cầu của bạn
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    });
                    googleRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("*/*"); // Chọn tất cả loại tập tin, thay đổi theo nhu cầu của bạn
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    });
                    otherRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("*/*"); // Chọn tất cả loại tập tin, thay đổi theo nhu cầu của bạn
                            startActivityForResult(intent, REQUEST_CODE);
                        }
                    });
                    cancelRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            isItemSelected = false;
                        }
                    });
                }
            }
        });
        webPageRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isItemSelected) {
                    isItemSelected = true;
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    startActivity(intent);
                    isItemSelected = false;
                }
            }
        });
        labelRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isItemSelected) {
                    isItemSelected = true;
                    SharedPreferences sharedPreferences_click = getActivity().getSharedPreferences("CheckClick", Context.MODE_PRIVATE);
                    String check = sharedPreferences_click.getString("check", "");
                    if (check.equals("true")) {
                        Intent intent = new Intent(getContext(), SelectOptionActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), Lable_Intro_Activity.class);
                        startActivity(intent);
                    }
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("activity", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("activity", "lable");
                    editor.apply();
                    isItemSelected = false;
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {
                uri = data.getData();
                Log.i("Test_3", "Uri: " + uri.toString());
                String mimeType = getActivity().getContentResolver().getType(uri);
                Log.d("Test_4", "onActivityResult: " + mimeType);
                if (mimeType != null) {
                    if (mimeType.startsWith("image")) {
                        // The MIME type suggests the data is an image
                        try {
                            List<Bitmap> listBimapCopy = new ArrayList<>();
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                            String jobName = getString(R.string.app_name) + " Document";
                            listBimapCopy.add(bitmap);
                            printManager.print(jobName, new ImagePrintDocumentAdapter(getContext(), listBimapCopy), null);
                            // Now you can use the Bitmap
                            // Now you can use the Bitmap
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if (mimeType.equals("application/pdf")) {
                        // The MIME type suggests the data is a PDF file
                        // Handle PDF file

                        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                        String jobName = getString(R.string.app_name) + " Document";
                        printManager.print(jobName, new PrintDocumentAdapter() {
                            @Override
                            public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
                                if (cancellationSignal.isCanceled()) {
                                    callback.onLayoutCancelled();
                                    return;
                                }

                                PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder("file name")
                                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                                        .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN);

                                PrintDocumentInfo info = builder.build();
                                callback.onLayoutFinished(info, true);
                            }

                            @Override
                            public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
                                InputStream in = null;
                                OutputStream out = null;

                                try {
                                    in = getActivity().getContentResolver().openInputStream(uri);
                                    out = new FileOutputStream(destination.getFileDescriptor());

                                    byte[] buf = new byte[1024];
                                    int len;

                                    while ((len = in.read(buf)) > 0) {
                                        out.write(buf, 0, len);
                                    }

                                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

                                } catch (IOException e) {
                                    callback.onWriteFailed(e.toString());
                                } finally {
                                    try {
                                        in.close();
                                        out.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new PrintAttributes.Builder().build());


//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(uri, "application/pdf");
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        startActivity(Intent.createChooser(intent, "Open With"));
//                        try {
//                            // The URI represents a PDF document
//                            ParcelFileDescriptor fileDescriptor =
//                            getActivity().getContentResolver().openFileDescriptor(uri, "r");
//                            if (fileDescriptor != null) {
//                                PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);
//
//                                final int pageCount = pdfRenderer.getPageCount();
//                                for (int i = 0; i < pageCount; i++) {
//                                    PdfRenderer.Page page = pdfRenderer.openPage(i);
//
//                                    // say we render for showing on the screen
//                                    Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(),
//                                            Bitmap.Config.ARGB_8888);
//
//                                    // Here, if needed, you can subsample the bitmap if it's too large
//                                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//
//                                    // do stuff with the bitmap
//
//                                    // close the page
//                                    page.close();
//                                }
//
//                                // close the renderer
//                                pdfRenderer.close();
//                                fileDescriptor.close();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                }
                // Perform operations on the document using its URI.

            }
        }
    }

}