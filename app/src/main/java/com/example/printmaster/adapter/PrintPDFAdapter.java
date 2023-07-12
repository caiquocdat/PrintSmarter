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

public class PrintPDFAdapter extends PrintDocumentAdapter {
    private Context context;
    private Bitmap bitmap;

    public PrintPDFAdapter(Context context, Bitmap bitmap) {
        this.context = context;
        this.bitmap = bitmap;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder("file name")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1);

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

        // Start a page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Draw the bitmap on the canvas
        canvas.drawBitmap(bitmap, 0, 0, null);

        pdfDocument.finishPage(page);

        try {
            pdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.getMessage());
            return;
        } finally {
            pdfDocument.close();
        }

        if (cancellationSignal.isCanceled()) {
            callback.onWriteCancelled();
            return;
        }

        callback.onWriteFinished(new PageRange[]{new PageRange(0, 0)});
    }
}
