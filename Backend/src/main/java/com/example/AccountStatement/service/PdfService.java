package com.example.AccountStatement.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Enumeration;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.AccountStatement.model.dto.AccountStatement;
// import com.itextpdf.commons.utils.Base64.InputStream;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;

@Service
public class PdfService {

    @Autowired
    private TemplateEngine templateEngine;

    static {
        // Register BouncyCastle FIPS as a security provider
        if (Security.getProvider(BouncyCastleFipsProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleFipsProvider());
        }
    }

    public ByteArrayInputStream generatePdf(AccountStatement accountStatement) {
        // Create Thymeleaf context and set variables
        Context context = new Context();
        context.setVariable("accountStatement", accountStatement);

        // Process Thymeleaf template to generate HTML content
        String htmlContent = templateEngine.process("account_statement", context);

        // Create output stream for PDF
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Create PdfWriter instance
            PdfWriter writer = new PdfWriter(out);

            // Create PdfDocument instance
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Convert HTML to PDF using iText's HtmlConverter
            HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), pdfDoc);

        } catch (IOException e) {
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @SuppressWarnings("deprecation")
    public ByteArrayInputStream generatePdfWithSignature(AccountStatement accountStatement) {
        ByteArrayInputStream pdfStream = generatePdf(accountStatement);

        ByteArrayOutputStream signedOut = new ByteArrayOutputStream();

        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            InputStream keystoreStream = (InputStream) getClass().getResourceAsStream("/keystore.p12");

            if (keystoreStream == null) {
                throw new FileNotFoundException("Keystore file not found");
            }

            ks.load(keystoreStream, "password".toCharArray());

            // Check if the KeyStore contains any aliases
            Enumeration<String> aliases = ks.aliases();
            if (!aliases.hasMoreElements()) {
                throw new Exception("The KeyStore does not contain any aliases.");
            }

            String alias = "myalias";
            PrivateKey pk = (PrivateKey) ks.getKey(alias, "password".toCharArray());
            Certificate[] chain = ks.getCertificateChain(alias);

            System.out.println("Alias: " + alias);
            System.out.println("Private Key: " + pk);

            try (PdfReader reader = new PdfReader(pdfStream); PdfWriter signWriter = new PdfWriter(signedOut)) {
                PdfSigner signer = new PdfSigner(reader, signWriter, new StampingProperties());

                Rectangle rect = new Rectangle(64, 650, 200, 50);
                PdfSignatureAppearance appearance = signer.getSignatureAppearance();
                appearance
                    .setReason("Account Statement Verification")
                    .setLocation("Location")
                    .setPageRect(rect)
                    .setPageNumber(1)
                    .setReuseAppearance(false);
                signer.setFieldName("sig");
                
                // signer.signDetached(new BouncyCastleDigest(), new PrivateKeySignature(pk, DigestAlgorithms.SHA256, "BC"), chain, null, null, null, 0, PdfSigner.CryptoStandard.CMS);

                signer.signDetached(
                new BouncyCastleDigest(), // FIPS-compliant digest
                new PrivateKeySignature(pk, DigestAlgorithms.SHA256, "BCFIPS"), // Use "BCFIPS" as provider
                chain, null, null, null, 0, PdfSigner.CryptoStandard.CMS
            );
                
            }
        } 
        catch (FileNotFoundException e) {
            System.err.println("Keystore file not found: " + e.getMessage());}
        catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        return new ByteArrayInputStream(signedOut.toByteArray());
    }
}