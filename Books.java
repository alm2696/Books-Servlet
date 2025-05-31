package mod04_02;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Books servlet serves the content of "20,000 Leagues Under the Sea"
 * by reading the book from a text file and displaying it in an HTML format.
 * This servlet is mapped to the URL pattern "/Books".
 * 
 * @author angel
 */
@WebServlet("/Books")
public class Books extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for the Books servlet.
     * Calls the HttpServlet constructor to initialize the servlet.
     */
    public Books() {
        super();
    }

    /**
     * Handles GET requests. Reads the content of "20000Leagues.txt" from
     * the server, formats it as HTML, and sends it as the response.
     * 
     * @param request the HttpServletRequest object, containing the client's request
     * @param response the HttpServletResponse object, used to send the response back to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during file reading or writing the response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set the response content type to HTML with UTF-8 encoding to ensure proper rendering of special characters
        response.setContentType("text/html; charset=UTF-8");

        // Start of the HTML document with title "Books"
        String HTMLStart = """
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>%s</title>
</head>
<body>
            """.formatted("Books");

        // End of the HTML document
        String HTMLEnd = """
</body>
</html>
                """;

        // Get the real path of the book file ("20000Leagues.txt") in the server's context
        ServletContext app = getServletConfig().getServletContext();
        String path = app.getRealPath("20000Leagues.txt");
        File book = new File(path);

        // StringBuilder is used to build the HTML content
        StringBuilder HTMLBody = new StringBuilder();

        // Check if the book file exists on the server
        if (book.exists()) {
            // Try reading the book file and appending each line as a new HTML paragraph
            try (Scanner fileIn = new Scanner(book)) {
                while (fileIn.hasNextLine()) {
                    String line = fileIn.nextLine();
                    HTMLBody.append("<p>").append(line).append("</p>"); // Wrap each line in a <p> tag
                }
            }
        }

        // Obtain the PrintWriter to write the response back to the client
        try (PrintWriter writer = response.getWriter()) {
            // Write the constructed HTML content
            writer.println(HTMLStart); // Start of HTML
            writer.println(HTMLBody.toString()); // Book content or error message
            writer.println(HTMLEnd); // End of HTML
        }
    }
}
