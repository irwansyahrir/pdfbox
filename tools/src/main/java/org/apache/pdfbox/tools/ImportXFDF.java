/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.File;
import java.io.IOException;


/**
 * This example will take a PDF document and fill the fields with data from the
 * XFDF fields.
 *
 * @author Ben Litchfield
 */
public class ImportXFDF
{
    /**
     * Creates a new instance of ImportFDF.
     */
    public ImportXFDF()
    {
    }

    /**
     * This will takes the values from the fdf document and import them into the
     * PDF document.
     *
     * @param pdfDocument The document to put the fdf data into.
     * @param fdfDocument The FDF document to get the data from.
     *
     * @throws IOException If there is an error setting the data in the field.
     */
    public void importFDF( PDDocument pdfDocument, FDFDocument fdfDocument ) throws IOException
    {
        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        acroForm.setCacheFields( true );
        acroForm.importFDF( fdfDocument );
    }

    /**
     * This will import an fdf document and write out another pdf.
     * <br>
     * see usage() for commandline
     *
     * @param args command line arguments
     *
     * @throws IOException If there is an error importing the FDF document.
     */
    public static void main(String[] args) throws IOException
    {
        // suppress the Dock icon on OS X
        System.setProperty("apple.awt.UIElement", "true");

        ImportXFDF importer = new ImportXFDF();
        importer.importXFDF( args );
    }

    private void importXFDF( String[] args ) throws IOException
    {
        if( args.length != 3 )
        {
            usage();
        }
        else
        {
            ImportFDF importer = new ImportFDF();
            try (PDDocument pdf = Loader.loadPDF(new File(args[0]));
                    FDFDocument fdf = Loader.loadXFDF(args[1]))
            {
                importer.importFDF( pdf, fdf );
                pdf.save( args[2] );
            }
        }
    }

    /**
     * This will print out a message telling how to use this example.
     */
    private static void usage()
    {
        System.err.println( "usage: org.apache.pdfbox.tools.ImportXFDF <pdf-file> <fdf-file> <output-file>" );
        System.exit(1);
    }
}
