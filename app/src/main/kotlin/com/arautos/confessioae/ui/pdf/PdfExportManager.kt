package com.arautos.confessioae.ui.pdf

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.text.StaticLayout
import android.text.TextPaint
import android.widget.Toast
import com.arautos.confessioae.data.model.ExamEntry
import java.io.FileOutputStream

class PdfExportManager(private val context: Context) {

    fun exportRoteiro(
        lastConfessionText: String?,
        userCondition: String?,
        entries: List<ExamEntry>,
        explanations: Map<String, String>
    ) {
        Toast.makeText(context, "Gerando PDF...", Toast.LENGTH_SHORT).show()

        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = "Roteiro_Confissao_${System.currentTimeMillis()}"

        printManager.print(jobName, object : PrintDocumentAdapter() {
            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes,
                cancellationSignal: CancellationSignal?,
                callback: LayoutResultCallback,
                extras: Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback.onLayoutCancelled()
                    return
                }

                val pdi = PrintDocumentInfo.Builder("roteiro_confissao.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .build()

                callback.onLayoutFinished(pdi, true)
            }

            override fun onWrite(
                pages: Array<out PageRange>?,
                destination: ParcelFileDescriptor,
                cancellationSignal: CancellationSignal?,
                callback: WriteResultCallback
            ) {
                val pdfDocument = PdfDocument()
                
                // A4 size in points (1/72 inch)
                val pageWidth = 595
                val pageHeight = 842
                
                var currentPageNumber = 1
                var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
                var page = pdfDocument.startPage(pageInfo)
                var canvas = page.canvas

                val margin = 36f
                val columnGap = 20f
                val colWidth = (pageWidth - 2 * margin - columnGap) / 2f
                
                var currentY = margin
                var currentX = margin
                var currentColumn = 0

                val titlePaint = TextPaint().apply {
                    color = Color.BLACK
                    textSize = 14f
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }

                val bodyPaint = TextPaint().apply {
                    color = Color.BLACK
                    textSize = 10f
                }

                val italicPaint = TextPaint().apply {
                    color = Color.BLACK
                    textSize = 10f
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
                }

                val boldPaint = TextPaint().apply {
                    color = Color.BLACK
                    textSize = 10f
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }

                fun drawText(text: String, paint: TextPaint, spacing: Float = 4f) {
                    val layout = StaticLayout.Builder.obtain(text, 0, text.length, paint, colWidth.toInt())
                        .setAlignment(android.text.Layout.Alignment.ALIGN_NORMAL)
                        .build()

                    if (currentY + layout.height > pageHeight - margin) {
                        if (currentColumn == 0) {
                            currentColumn = 1
                            currentX = margin + colWidth + columnGap
                            currentY = margin
                        } else {
                            // Finish current page and start a new one
                            pdfDocument.finishPage(page)
                            currentPageNumber++
                            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
                            page = pdfDocument.startPage(pageInfo)
                            canvas = page.canvas
                            currentColumn = 0
                            currentX = margin
                            currentY = margin
                        }
                    }

                    canvas.save()
                    canvas.translate(currentX, currentY)
                    layout.draw(canvas)
                    canvas.restore()
                    currentY += layout.height + spacing
                }

                // --- CONTENT GENERATION ---
                drawText("INÍCIO", titlePaint, 8f)
                drawText("Em nome do Pai, do Filho e do Espírito Santo. Amém.", bodyPaint, 6f)
                drawText("Padre, eu pequei.", boldPaint, 6f)
                
                lastConfessionText?.let { drawText(it, boldPaint, 6f) }
                if (!userCondition.isNullOrBlank()) {
                    drawText("Sou $userCondition", boldPaint, 6f)
                }
                
                drawText("CONFISSÃO DOS PECADOS", titlePaint, 8f)
                
                val filteredEntries = entries.filter { it !is ExamEntry.PermanentAdd }
                if (filteredEntries.isEmpty()) {
                    drawText("Nenhum pecado selecionado no exame.", italicPaint, 6f)
                } else {
                    filteredEntries.forEach { entry ->
                        val explanation = explanations[entry.id]
                        if (!explanation.isNullOrBlank()) {
                            drawText(explanation, boldPaint, 2f)
                            drawText(entry.text, italicPaint, 6f)
                        } else {
                            drawText(entry.text, boldPaint, 6f)
                        }
                    }
                }
                
                drawText("... e estes são os meus pecados.", boldPaint, 12f)
                
                drawText("ATO DE CONTRIÇÃO", titlePaint, 8f)
                drawText("Meu Deus, eu me arrependo de todo o coração de Vos ter ofendido, porque sois tão bom e amável. Prometo, com a vossa graça, não tornar a pecar e evitar as ocasiões de pecado. Amém.", italicPaint, 12f)
                
                drawText("ABSOLVIÇÃO", titlePaint, 8f)
                drawText("Em nome do Pai, do Filho e do Espírito Santo. Amém.", boldPaint, 6f)

                pdfDocument.finishPage(page)

                try {
                    pdfDocument.writeTo(FileOutputStream(destination.fileDescriptor))
                    callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                    Toast.makeText(context, "PDF gerado com sucesso", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    callback.onWriteFailed(e.message)
                } finally {
                    pdfDocument.close()
                }
            }
        }, null)
    }
}
