// package com.example.AccountStatement;

// import com.example.AccountStatement.model.dto.AccountStatement;
// import com.example.AccountStatement.service.PdfService;
// import com.example.AccountStatement.service.AccountStatementService;

// import java.io.ByteArrayInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class TestConfig {

//     @Autowired
//     private AccountStatementService transactionService;

//     @Autowired
//     private PdfService pdfService;

//     @Bean
//     public CommandLineRunner testPDFGeneration() {
//         return args -> {
//             String accountNumber = "1234567890"; // Example account number
//             String fromDate = "2021-01-01"; // Example start date
//             String toDate = "2021-01-31"; // Example end date

//             // Generate account statement
//             AccountStatement accountStatement = transactionService.getAccountStatement(accountNumber, fromDate, toDate);

//             // Generate PDF
//             ByteArrayInputStream pdfStream = pdfService.generatePdf(accountStatement);
//             byte[] pdfBytes = pdfStream.readAllBytes();

//             // Save PDF to file for verification
//             try (FileOutputStream fos = new FileOutputStream("account_statement2.pdf")) {
//                 fos.write(pdfBytes);
//                 System.out.println("PDF generated successfully and saved as account_statement.pdf");
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         };
//     }
// }
