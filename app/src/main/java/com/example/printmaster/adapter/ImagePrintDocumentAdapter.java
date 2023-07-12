package com.example.printmaster.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ImagePrintDocumentAdapter extends PrintDocumentAdapter {
    Context context;
    List<Bitmap> bitmapList;

    public ImagePrintDocumentAdapter(Context context, List<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder("file name")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_PHOTO)
                .setPageCount(bitmapList.size());

        PrintDocumentInfo info = builder.build();
        callback.onLayoutFinished(info, !newAttributes.equals(oldAttributes));
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        PrintAttributes printAttributes = new PrintAttributes.Builder()
                .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 300, 300))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build();

        PdfDocument pdfDocument = new PrintedPdfDocument(context, printAttributes);

        try {
            for (int i = 0; i < bitmapList.size(); i++) {
                Bitmap bitmap = bitmapList.get(i);
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), i).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                Canvas canvas = page.getCanvas();

                float scaleFactor = Math.min(
                        (float) canvas.getWidth() / bitmap.getWidth(),
                        (float) canvas.getHeight() / bitmap.getHeight()
                );

                int newWidth = (int) (bitmap.getWidth() * scaleFactor);
                int newHeight = (int) (bitmap.getHeight() * scaleFactor);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

                int left = (canvas.getWidth() - newWidth) / 2;
                int top = (canvas.getHeight() - newHeight) / 2;

                canvas.drawBitmap(scaledBitmap, left, top, null);

                pdfDocument.finishPage(page);
            }

            pdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
            callback.onWriteFinished(pages);
        } catch (IOException e) {
            callback.onWriteFailed(e.getMessage());
        } finally {
            pdfDocument.close();
        }

        if (cancellationSignal.isCanceled()) {
            callback.onWriteCancelled();
        }
    }
}
