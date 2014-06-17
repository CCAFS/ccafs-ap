/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.util;

import java.awt.Color;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;


public class HeaderFooterPdf extends PdfPageEventHelper {

  private final static Font HEADER_FONT = new Font(FontFactory.getFont("Arial", 12, Color.GRAY));
  private final static Font FOOTER_FONT = new Font(FontFactory.getFont("Arial", 12, Color.GRAY));

  private String headerText;
  private int pagenumber;
  private int pageOrientation;

  public HeaderFooterPdf(String headerText, int pageOrientation) {
    this.headerText = headerText;
    this.pageOrientation = pageOrientation;
  }

  /**
   * Adds the header and the footer.
   */
  @Override
  public void onEndPage(PdfWriter writer, Document document) {
    if (pagenumber > 1) {
      Rectangle rect = writer.getBoxSize("art");
      int xPos, yPos;

      // Header
      Phrase phrase = new Phrase();
      phrase.setFont(HEADER_FONT);
      phrase.add(headerText);
      ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, phrase, rect.getRight(),
        rect.getTop(), 0);

      // Footer
      phrase = new Phrase();
      phrase.setFont(FOOTER_FONT);
      phrase.add(String.valueOf(pagenumber));
      ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrase, rect.getRight(),
        rect.getBottom(), 0);
    }
  }

  /**
   * Increase the page number.
   */
  @Override
  public void onStartPage(PdfWriter writer, Document document) {
    pagenumber++;
  }
}
