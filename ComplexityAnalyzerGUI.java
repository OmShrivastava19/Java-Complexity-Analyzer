import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ComplexityAnalyzerGUI extends JFrame {
    
    private JTextArea codeArea;
    private JLabel timeComplexityLabel;
    private JLabel spaceComplexityLabel;
    private JButton analyzeButton;
    
    public ComplexityAnalyzerGUI() {
        setTitle("Java Complexity Analyzer");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Java Code Complexity Analyzer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Code input area
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        JLabel inputLabel = new JLabel("Enter Java Code:");
        centerPanel.add(inputLabel, BorderLayout.NORTH);
        
        codeArea = new JTextArea();
        codeArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        codeArea.setLineWrap(false);
        codeArea.setTabSize(4);
        JScrollPane scrollPane = new JScrollPane(codeArea);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel with button and results
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        analyzeButton = new JButton("Analyze Complexity");
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        analyzeButton.addActionListener(e -> analyzeCode());
        bottomPanel.add(analyzeButton);
        
        bottomPanel.add(Box.createVerticalStrut(15));
        
        // Results panel
        JPanel resultsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Analysis Results"));
        
        timeComplexityLabel = new JLabel("Time Complexity: -");
        timeComplexityLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        resultsPanel.add(timeComplexityLabel);
        
        spaceComplexityLabel = new JLabel("Space Complexity: -");
        spaceComplexityLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        resultsPanel.add(spaceComplexityLabel);
        
        bottomPanel.add(resultsPanel);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void analyzeCode() {
        String code = codeArea.getText().trim();
        
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter some code to analyze!", 
                "No Code", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String timeComplexity = analyzeTimeComplexity(code);
        String spaceComplexity = analyzeSpaceComplexity(code);
        
        timeComplexityLabel.setText("Time Complexity: " + timeComplexity);
        spaceComplexityLabel.setText("Space Complexity: " + spaceComplexity);
    }
    
    private String analyzeTimeComplexity(String code) {
        int nestedLoops = countNestedLoops(code);
        
        if (code.contains("Collections.sort") || code.contains("Arrays.sort")) {
            return "O(n log n) - Sorting operation detected";
        }
        
        if (nestedLoops >= 3) {
            return "O(n^" + nestedLoops + ") - " + nestedLoops + " nested loops detected";
        } else if (nestedLoops == 2) {
            return "O(n^2) - Nested loops detected";
        } else if (nestedLoops == 1) {
            if (containsDivideAndConquer(code)) {
                return "O(log n) - Loop with division/halving detected";
            }
            return "O(n) - Single loop detected";
        } else if (containsRecursion(code)) {
            if (containsDivideAndConquer(code)) {
                return "O(log n) or O(n log n) - Recursive divide and conquer detected";
            }
            return "O(2^n) or higher - Recursion detected (depends on structure)";
        }
        
        return "O(1) - Constant time";
    }
    
    private String analyzeSpaceComplexity(String code) {
        boolean hasArray = Pattern.compile("(new\\s+\\w+\\[|\\[\\])").matcher(code).find();
        boolean hasList = code.contains("ArrayList") || code.contains("LinkedList") || 
                         code.contains("List<");
        boolean hasMap = code.contains("HashMap") || code.contains("Map<");
        boolean hasRecursion = containsRecursion(code);
        
        if (hasRecursion) {
            return "O(n) - Recursion uses call stack space";
        }
        
        if (hasArray || hasList || hasMap) {
            return "O(n) - Data structure allocation detected";
        }
        
        return "O(1) - Constant space";
    }
    
    private int countNestedLoops(String code) {
        int maxDepth = 0;
        int currentDepth = 0;
        
        String[] lines = code.split("\n");
        
        for (String line : lines) {
            String trimmed = line.trim();
            
            if (trimmed.matches("for\\s*\\(.*\\).*") || 
                trimmed.matches("while\\s*\\(.*\\).*") ||
                trimmed.matches("do\\s*\\{?.*")) {
                currentDepth++;
                maxDepth = Math.max(maxDepth, currentDepth);
            }
            
            if (trimmed.equals("}")) {
                currentDepth = Math.max(0, currentDepth - 1);
            }
        }
        
        return maxDepth;
    }
    
    private boolean containsRecursion(String code) {
        Pattern methodPattern = Pattern.compile("(public|private|protected)?\\s*(static)?\\s*\\w+\\s+(\\w+)\\s*\\(");
        Matcher matcher = methodPattern.matcher(code);
        
        while (matcher.find()) {
            String methodName = matcher.group(3);
            if (methodName != null && code.indexOf(methodName + "(", matcher.end()) > 0) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean containsDivideAndConquer(String code) {
        return code.contains("/= 2") || code.contains("/ 2") || 
               code.contains(">>> 1") || code.contains(">> 1") ||
               Pattern.compile("\\w+\\s*=\\s*\\w+\\s*/\\s*2").matcher(code).find();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ComplexityAnalyzerGUI gui = new ComplexityAnalyzerGUI();
            gui.setVisible(true);
        });
    }
}