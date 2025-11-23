import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ComplexityAnalyzer {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Java Code Complexity Analyzer ===");
        System.out.println("Enter your Java code (type 'END' on a new line to finish):\n");
        
        StringBuilder code = new StringBuilder();
        String line;
        
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.trim().equals("END")) {
                break;
            }
            code.append(line).append("\n");
        }
        
        String javaCode = code.toString();
        
        System.out.println("\n=== Analysis Results ===");
        System.out.println("Time Complexity: " + analyzeTimeComplexity(javaCode));
        System.out.println("Space Complexity: " + analyzeSpaceComplexity(javaCode));
        
        scanner.close();
    }
    
    private static String analyzeTimeComplexity(String code) {
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
    
    private static String analyzeSpaceComplexity(String code) {
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
    
    private static int countNestedLoops(String code) {
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
    
    private static boolean containsRecursion(String code) {
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
    
    private static boolean containsDivideAndConquer(String code) {
        return code.contains("/= 2") || code.contains("/ 2") || 
               code.contains(">>> 1") || code.contains(">> 1") ||
               Pattern.compile("\\w+\\s*=\\s*\\w+\\s*/\\s*2").matcher(code).find();
    }
}



// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.util.Stack;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// /**
//  * A Java Swing application that estimates the Time and Space complexity
//  * of a given Java code snippet using Static Analysis heuristics.
//  *
//  * NOTE: Determining exact complexity for arbitrary code is theoretically impossible
//  * (The Halting Problem). This tool estimates based on common structural patterns
//  * like loop nesting, recursion, and array allocation.
//  */
// public class ComplexityAnalyzer extends JFrame {

//     private JTextArea inputArea;
//     private JLabel timeResultLabel;
//     private JLabel spaceResultLabel;
//     private JTextArea detailsArea;

//     public ComplexityAnalyzer() {
//         setTitle("Code Complexity Analyzer (Big O Estimator)");
//         setSize(900, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout(10, 10));

//         // UI Setup
//         initComponents();
//     }

//     private void initComponents() {
//         // --- Header ---
//         JPanel headerPanel = new JPanel();
//         headerPanel.setBackground(new Color(60, 63, 65));
//         JLabel titleLabel = new JLabel("Java Complexity Analyzer");
//         titleLabel.setForeground(Color.WHITE);
//         titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
//         headerPanel.add(titleLabel);
//         add(headerPanel, BorderLayout.NORTH);

//         // --- Center (Input and Output) ---
//         JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//         splitPane.setResizeWeight(0.5);

//         // Input Panel
//         JPanel inputPanel = new JPanel(new BorderLayout());
//         inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//         JLabel inputLabel = new JLabel("Enter your Java Code here:");
//         inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
//         inputPanel.add(inputLabel, BorderLayout.NORTH);

//         inputArea = new JTextArea();
//         inputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
//         inputArea.setText(getDefaultCode()); // Pre-fill with example
//         inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

//         // Output Panel
//         JPanel outputPanel = new JPanel(new BorderLayout());
//         outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
//         JPanel resultSummaryPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        
//         timeResultLabel = new JLabel("Time Complexity: N/A");
//         timeResultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
//         timeResultLabel.setForeground(new Color(200, 0, 0));
        
//         spaceResultLabel = new JLabel("Space Complexity: N/A");
//         spaceResultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
//         spaceResultLabel.setForeground(new Color(0, 100, 200));

//         resultSummaryPanel.add(timeResultLabel);
//         resultSummaryPanel.add(spaceResultLabel);

//         detailsArea = new JTextArea();
//         detailsArea.setEditable(false);
//         detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
//         detailsArea.setBackground(new Color(240, 240, 240));
//         detailsArea.setBorder(BorderFactory.createTitledBorder("Analysis Details"));

//         outputPanel.add(resultSummaryPanel, BorderLayout.NORTH);
//         outputPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

//         splitPane.setLeftComponent(inputPanel);
//         splitPane.setRightComponent(outputPanel);
//         add(splitPane, BorderLayout.CENTER);

//         // --- Bottom (Buttons) ---
//         JPanel buttonPanel = new JPanel();
//         JButton analyzeButton = new JButton("Analyze Complexity");
//         analyzeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
//         analyzeButton.setBackground(new Color(75, 180, 75));
//         analyzeButton.setForeground(Color.WHITE);
//         analyzeButton.setFocusPainted(false);
        
//         analyzeButton.addActionListener((ActionEvent e) -> performAnalysis());
        
//         buttonPanel.add(analyzeButton);
//         add(buttonPanel, BorderLayout.SOUTH);
//     }

//     private void performAnalysis() {
//         String rawCode = inputArea.getText();
//         if (rawCode.trim().isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Please enter code to analyze.");
//             return;
//         }

//         // 1. Preprocess Code (Remove comments and string literals to avoid false positives)
//         String cleanCode = cleanCode(rawCode);

//         // 2. Analyze Time Complexity
//         AnalysisResult timeResult = estimateTimeComplexity(cleanCode);
//         timeResultLabel.setText("Time Complexity: " + timeResult.complexity);

//         // 3. Analyze Space Complexity
//         AnalysisResult spaceResult = estimateSpaceComplexity(cleanCode);
//         spaceResultLabel.setText("Space Complexity: " + spaceResult.complexity);

//         // 4. Update Details
//         StringBuilder sb = new StringBuilder();
//         sb.append("--- TIME COMPLEXITY LOGIC ---\n");
//         sb.append(timeResult.explanation).append("\n\n");
//         sb.append("--- SPACE COMPLEXITY LOGIC ---\n");
//         sb.append(spaceResult.explanation);
//         detailsArea.setText(sb.toString());
//     }

//     // --- Core Logic: Code Cleaning ---
//     private String cleanCode(String code) {
//         // Remove single line comments
//         code = code.replaceAll("//.*", "");
//         // Remove block comments
//         code = code.replaceAll("/\\*[\\s\\S]*?\\*/", "");
//         // Remove string literals (so "for" inside a string isn't counted)
//         code = code.replaceAll("\".*?\"", "\"\"");
//         return code;
//     }

//     // --- Core Logic: Time Complexity ---
//     private AnalysisResult estimateTimeComplexity(String code) {
//         int maxDepth = 0;
//         int currentDepth = 0;
//         boolean isLogarithmic = false;
//         boolean hasRecursion = false;
        
//         StringBuilder log = new StringBuilder();
        
//         // Simple Recursive Check (looks for function name calling itself)
//         // This is a naive check requiring standard method syntax
//         Pattern methodPattern = Pattern.compile("([a-zA-Z0-9_]+)\\s*\\(");
//         Matcher m = methodPattern.matcher(code);
//         while(m.find()) {
//             String methodName = m.group(1);
//             if(!methodName.equals("for") && !methodName.equals("while") && !methodName.equals("if")) {
//                  // Check if this name appears inside the body (Rough heuristic)
//                  if (code.indexOf(methodName + "(", m.end()) != -1) {
//                      hasRecursion = true;
//                      log.append("- Recursion detected (Method: ").append(methodName).append(").\n");
//                  }
//             }
//         }

//         // Loop Analysis
//         String[] lines = code.split("\n");
//         for (String line : lines) {
//             line = line.trim();
            
//             // Check for loop start
//             if (line.startsWith("for") || line.startsWith("while")) {
//                 currentDepth++;
//                 if (currentDepth > maxDepth) maxDepth = currentDepth;
                
//                 log.append("- Found Loop at depth ").append(currentDepth).append(": ").append(line).append("\n");

//                 // Check for O(log n) patterns (multiplying/dividing iterator)
//                 if (line.contains("*=") || line.contains("/=") || line.contains("* 2") || line.contains("/ 2")) {
//                     isLogarithmic = true;
//                     log.append("  -> Detected logarithmic step pattern (*= or /=).\n");
//                 }
//             }
            
//             // Check for loop close (naive brace matching)
//             // Note: This assumes standard indentation/bracing. A real parser is needed for perfect accuracy.
//             if (line.contains("}")) {
//                 // Only decrement if we are actually deep (simple heuristic)
//                 if (currentDepth > 0) {
//                      // We only decrement depth if we think this brace belongs to a loop. 
//                      // In a simple analyzer, we often treat all } as scope closers.
//                      currentDepth--; 
//                 }
//             }
//         }

//         String complexity;
//         if (hasRecursion && maxDepth == 0) {
//              complexity = "O(N) or O(2^N)";
//              log.append("Recursion detected without explicit loops. Usually O(N) or exponential.");
//         } else if (maxDepth == 0) {
//             complexity = "O(1)";
//             log.append("No loops or recursion detected.");
//         } else if (isLogarithmic && maxDepth == 1) {
//             complexity = "O(log N)";
//         } else if (isLogarithmic && maxDepth > 1) {
//             complexity = "O(N^" + (maxDepth - 1) + " log N)";
//         } else if (maxDepth == 1) {
//             complexity = "O(N)";
//         } else if (maxDepth == 2) {
//             complexity = "O(N^2)";
//         } else {
//             complexity = "O(N^" + maxDepth + ")";
//         }

//         return new AnalysisResult(complexity, log.toString());
//     }

//     // --- Core Logic: Space Complexity ---
//     private AnalysisResult estimateSpaceComplexity(String code) {
//         StringBuilder log = new StringBuilder();
//         boolean hasArray = false;
//         boolean hasMatrix = false;
//         boolean hasCollection = false;
        
//         // Check for 1D Arrays
//         if (code.matches(".*new\\s+\\w+\\s*\\[.*\\].*")) {
//             hasArray = true;
//             log.append("- Detected Array allocation (new Type[...]).\n");
//         }
        
//         // Check for 2D Arrays
//         if (code.matches(".*new\\s+\\w+\\s*\\[.*\\]\\s*\\[.*\\].*")) {
//             hasMatrix = true;
//             log.append("- Detected 2D Matrix allocation (new Type[...][...]).\n");
//         }
        
//         // Check for Collections (ArrayList, HashMap, etc) with variable size
//         if (code.contains("ArrayList") || code.contains("HashMap") || code.contains("HashSet")) {
//             hasCollection = true;
//             log.append("- Detected Collection Framework usage (potential O(N) space).\n");
//         }

//         // Recursion check for Stack Space
//         boolean hasRecursion = log.toString().contains("Recursion"); // Re-using logic would be better, but keeping simple

//         String complexity = "O(1)";
        
//         if (hasMatrix) {
//             complexity = "O(N^2)";
//         } else if (hasArray || hasCollection) {
//             complexity = "O(N)";
//         } else {
//              // If recursive, stack space is O(N)
//              // We'll leave it as O(1) + Note unless we confirmed recursion depth.
//              log.append("- No significant auxiliary structure found (Variables are O(1)).");
//         }

//         return new AnalysisResult(complexity, log.toString());
//     }

//     private String getDefaultCode() {
//         return "public void bubbleSort(int[] arr) {\n" +
//                "    int n = arr.length;\n" +
//                "    for (int i = 0; i < n - 1; i++) {\n" +
//                "        for (int j = 0; j < n - i - 1; j++) {\n" +
//                "            if (arr[j] > arr[j + 1]) {\n" +
//                "                // swap temp and arr[i]\n" +
//                "                int temp = arr[j];\n" +
//                "                arr[j] = arr[j + 1];\n" +
//                "                arr[j + 1] = temp;\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//     }

//     // Helper class to store results
//     private static class AnalysisResult {
//         String complexity;
//         String explanation;

//         public AnalysisResult(String complexity, String explanation) {
//             this.complexity = complexity;
//             this.explanation = explanation;
//         }
//     }

//     public static void main(String[] args) {
//         try {
//             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         } catch (Exception ignored) {}
        
//         SwingUtilities.invokeLater(() -> {
//             new ComplexityAnalyzer().setVisible(true);
//         });
//     }
// }