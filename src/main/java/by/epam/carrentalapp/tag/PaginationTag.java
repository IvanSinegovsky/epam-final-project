package by.epam.carrentalapp.tag;

import lombok.Setter;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@Setter
public class PaginationTag extends TagSupport {
    private final Logger LOGGER = Logger.getLogger(PaginationTag.class);

    private static final String DISABLED_LINK = "<li><a class=\"page-link disabled\">...</a></li>";

    private final int N_PAGES_FIRST = 1;
    private final int N_PAGES_PREV = 2;
    private final int N_PAGES_NEXT = 2;
    private final int N_PAGES_LAST = 1;

    private boolean showAllPrev;
    private boolean showAllNext;

    private Integer currentPage;
    private Integer lastPage;
    private String command;

    @Override
    public int doStartTag() {
        showAllPrev = (N_PAGES_FIRST + N_PAGES_PREV + 1) >= currentPage;
        showAllNext = currentPage + N_PAGES_NEXT >= lastPage - N_PAGES_LAST;

        printPagination();

        return SKIP_BODY;
    }

    private void printPagination() {
        JspWriter writer = pageContext.getOut();
        try {
            int prevPage = currentPage - 1 > 0 ? currentPage - 1 : 1;
            writer.write("<ul class=\"pagination\">");
            writer.write(getLinkElement(prevPage, "&lt; Prev"));
            if (showAllPrev) {
                for (int i = 1; i <= currentPage - 1; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }

            } else {
                for (int i = 1; i <= N_PAGES_FIRST; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
                writer.write(DISABLED_LINK);
                for (int i = currentPage - N_PAGES_PREV; i <= currentPage - 1; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
            }
            writer.write(String.format(
                    "<li class=\"active page-item\"><a class=\"page-link\" href=\"home?command=%s&currentPage=%d\">%d</a></li>",
                    command, currentPage, currentPage
            ));
            if (showAllNext) {
                for (int i = currentPage + 1; i <= lastPage; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
            } else {
                for (int i = currentPage + 1; i <= currentPage + N_PAGES_NEXT; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
                writer.write(DISABLED_LINK);
                for (int i = lastPage - N_PAGES_LAST + 1; i <= lastPage; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
            }
            int nextPage = Math.min(currentPage + 1, lastPage);
            writer.write(getLinkElement(nextPage, "Next &gt;"));
            writer.write("</ul>");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private String getLinkElement(int i, String text) {
        return String.format(
                "<li class=\"page-item\"><a class=\"page-link\" href=\"home?command=%s&currentPage=%d\">%s</a></li>",
                command, i, text);
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
