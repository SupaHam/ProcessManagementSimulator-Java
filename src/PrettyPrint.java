import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for tabular string formatting.
 */
public class PrettyPrint {

    private List<String> fields;
    private final List<List<?>> rows = new ArrayList<>();

    public PrettyPrint(String... fields) {
        this.fields = Arrays.asList(fields); // Copy fields ArrayList to make table immutable
    }

    public void addRow(Object... row) {
        // Only allow row size that is equal to column length to make table valid.
        if (row.length != fields.size()) {
            throw new RuntimeException("Row fields must equal header fields.");
        }
        this.rows.add(Arrays.asList(row));
    }

    public void print() {
        List<Integer> columnWidths = computeWidths();

        String separatorRow = generateSeperatorRow(columnWidths);
        System.out.println(separatorRow); // HEADER;
        System.out.println(generateRowString(fields, columnWidths));
        System.out.println(separatorRow); // fields separator;
        for (List<?> row : rows) {
            System.out.println(generateRowString(row, columnWidths));
        }
        System.out.println(separatorRow); // FOOTER
    }

    private String generateRowString(List<?> row, List<Integer> columnWidths) {
        String s = "| ";
        for (int i = 0; i < row.size(); i++) {
            String cell = row.get(i).toString();
            int spacesToAdd = columnWidths.get(i) - cell.length();
            spacesToAdd += 1; // suffix padding;
            s += cell + repeatString(" ", spacesToAdd) + "| ";

        }
        return s;
    }

    private List<Integer> computeWidths() {
        ArrayList<Integer> widths = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            int maxWidth = fields.get(i).length();
            for (List<?> row : rows) {
                String cell = row.get(i).toString();
                if (cell.length() > maxWidth) {
                    maxWidth = cell.length();
                }
            }
            widths.add(maxWidth);
        }
        return widths;
    }

    private String generateSeperatorRow(List<Integer> columnWidths) {
        String finalString = "+";
        for (Integer columnWidth : columnWidths) {
            finalString += repeatString("-", columnWidth + 2) + "+";
        }
        return finalString;
    }

    public static String repeatString(String s, int repeat) {
        return new String(new char[repeat]).replace("\0", s);
    }
}
