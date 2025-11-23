# Java Code Complexity Analyzer

A comprehensive tool for analyzing time and space complexity of Java code snippets through both console and GUI interfaces.

---

## 📋 Overview

The Java Code Complexity Analyzer is an educational tool designed to help developers and students understand the performance characteristics of their Java code. It uses pattern recognition and static code analysis techniques to automatically detect common algorithmic patterns and provide instant feedback on time and space complexity.

This project offers two user interfaces:
- **Console Version** (`ComplexityAnalyzer.java`) - Lightweight command-line interface for quick analysis
- **GUI Version** (`ComplexityAnalyzerGUI.java`) - User-friendly graphical interface with visual feedback

The tool analyzes code patterns including nested loops, recursion, sorting operations, data structure allocations, and divide-and-conquer patterns to estimate Big-O complexity.

---

## ✨ Features

### Core Analysis Capabilities
- **Time Complexity Detection**
  - Identifies nested loops and calculates O(n), O(n²), O(n³), etc.
  - Detects sorting operations (Arrays.sort, Collections.sort) → O(n log n)
  - Recognizes recursion patterns → O(2^n) or context-dependent
  - Identifies binary search and divide-and-conquer patterns → O(log n)
  - Recognizes constant time operations → O(1)

- **Space Complexity Analysis**
  - Detects array and collection allocations → O(n)
  - Identifies recursive calls using stack space → O(n)
  - Recognizes constant space usage → O(1)

### User Interfaces
- **Console Interface**
  - Simple text-based input/output
  - Type 'END' to complete code entry
  - Immediate analysis results
  - No GUI dependencies required

- **Graphical Interface**
  - Clean, minimalist design
  - Large code editor with monospaced font
  - Scrollable text area for long code snippets
  - One-click analysis button
  - Clear results display with explanations
  - Input validation with error dialogs

### Additional Features
- Multi-line code support
- Input validation and error handling
- Detailed explanations with complexity results
- Pattern-based heuristic analysis
- No compilation required - analyzes code as text
- Zero external dependencies

---

## 🛠️ Technologies/Tools Used

- **Programming Language:** Java
- **JDK Version:** Java 8 or higher
- **GUI Framework:** Java Swing (javax.swing)
- **Pattern Matching:** Java Regular Expressions (java.util.regex)
- **I/O Handling:** Java Scanner, BufferedReader
- **Development Tools:** 
  - Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or text editor
  - Java Compiler (javac)
  - Java Runtime Environment (JRE)

**Key Libraries Used:**
- `java.util.Scanner` - Console input handling
- `java.util.regex.Pattern` - Pattern matching for code analysis
- `java.util.regex.Matcher` - Regex operations
- `javax.swing.*` - GUI components
- `java.awt.*` - Layout managers and UI controls

**No External Dependencies** - The project uses only standard JDK libraries, making it highly portable and easy to run on any Java-enabled system.

---

## 📥 Installation & Setup

### Prerequisites
Ensure you have the following installed on your system:
- **Java Development Kit (JDK) 8 or higher**
  - Check version: `java -version`
  - Download from: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Terminal/Command Prompt** access
- **Text Editor or IDE** (optional but recommended)

### Installation Steps

1. **Download the Source Files**
   ```bash
   # Clone the repository (if using Git)
   git clone <repository-url>
   cd java-complexity-analyzer
   
   # Or download the following files directly:
   # - ComplexityAnalyzer.java
   # - ComplexityAnalyzerGUI.java
   # - README.md
   # - statement.md
   ```

2. **Compile the Programs**
   
   Open terminal/command prompt in the project directory and run:
   
   ```bash
   # Compile console version
   javac ComplexityAnalyzer.java
   
   # Compile GUI version
   javac ComplexityAnalyzerGUI.java
   ```
   
   This will generate `.class` files in the same directory.

3. **Verify Compilation**
   
   Check that the following files exist:
   - `ComplexityAnalyzer.class`
   - `ComplexityAnalyzerGUI.class`

---

## 🚀 How to Run

### Running the Console Version

```bash
java ComplexityAnalyzer
```

**Usage:**
1. The program will prompt you to enter Java code
2. Paste or type your code snippet
3. Type `END` on a new line when finished
4. Press Enter to see the analysis results

**Example:**
```
=== Java Code Complexity Analyzer ===
Enter your Java code (type 'END' on a new line to finish):

for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        System.out.println(i + j);
    }
}
END

=== Analysis Results ===
Time Complexity: O(n^2) - Nested loops detected
Space Complexity: O(1) - Constant space
```

### Running the GUI Version

```bash
java ComplexityAnalyzerGUI
```

**Usage:**
1. A window will open with the title "Java Code Complexity Analyzer"
2. Enter or paste your Java code in the text area
3. Click the **"Analyze Complexity"** button
4. View results in the "Analysis Results" panel at the bottom

---

## 🧪 Testing Instructions

### Manual Testing

#### Test Case 1: Single Loop (O(n))
```java
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
```
**Expected Output:**
- Time Complexity: O(n) - Single loop detected
- Space Complexity: O(1) - Constant space

#### Test Case 2: Nested Loops (O(n²))
```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        System.out.println(i + j);
    }
}
```
**Expected Output:**
- Time Complexity: O(n^2) - Nested loops detected
- Space Complexity: O(1) - Constant space

#### Test Case 3: Binary Search (O(log n))
```java
while (left <= right) {
    int mid = (left + right) / 2;
    if (arr[mid] == target) return mid;
    if (arr[mid] < target) left = mid + 1;
    else right = mid - 1;
}
```
**Expected Output:**
- Time Complexity: O(log n) - Loop with division/halving detected
- Space Complexity: O(1) - Constant space

#### Test Case 4: Recursion (O(2^n))
```java
int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n-1) + fibonacci(n-2);
}
```
**Expected Output:**
- Time Complexity: O(2^n) or higher - Recursion detected (depends on structure)
- Space Complexity: O(n) - Recursion uses call stack space

#### Test Case 5: Sorting (O(n log n))
```java
int[] arr = new int[n];
Arrays.sort(arr);
```
**Expected Output:**
- Time Complexity: O(n log n) - Sorting operation detected
- Space Complexity: O(n) - Data structure allocation detected

#### Test Case 6: Triple Nested Loop (O(n³))
```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
            // operation
        }
    }
}
```
**Expected Output:**
- Time Complexity: O(n^3) - 3 nested loops detected
- Space Complexity: O(1) - Constant space

#### Test Case 7: Array Allocation (O(n) space)
```java
int[] numbers = new int[n];
ArrayList<String> list = new ArrayList<>();
```
**Expected Output:**
- Time Complexity: O(1) - Constant time
- Space Complexity: O(n) - Data structure allocation detected

### Testing Checklist

- [ ] Console version compiles without errors
- [ ] GUI version compiles without errors
- [ ] Console version accepts multi-line input
- [ ] Console version recognizes 'END' command
- [ ] GUI window opens successfully
- [ ] GUI text area accepts input
- [ ] "Analyze Complexity" button is clickable
- [ ] Empty input shows error message (GUI)
- [ ] Single loop detected as O(n)
- [ ] Nested loops detected correctly (O(n²), O(n³))
- [ ] Sorting operations identified as O(n log n)
- [ ] Recursion detected appropriately
- [ ] Binary search patterns recognized as O(log n)
- [ ] Space complexity correctly identifies arrays/collections
- [ ] Space complexity recognizes recursive stack usage
- [ ] Results display with clear explanations
- [ ] Both versions produce consistent results for same input

### Edge Cases to Test

1. **Empty Input** - Should show error message
2. **Single Statement** - Should return O(1)
3. **Code with Comments** - Should handle gracefully
4. **Very Long Code (500+ lines)** - Should complete within 2 seconds
5. **Mixed Patterns** - Code with loops + recursion + sorting

---

## 📖 Usage Examples

### Example 1: Bubble Sort Analysis
```java
void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n-1; i++) {
        for (int j = 0; j < n-i-1; j++) {
            if (arr[j] > arr[j+1]) {
                int temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
    }
}
```
**Analysis Result:** Time O(n²), Space O(1)

### Example 2: Merge Sort Analysis
```java
void mergeSort(int[] arr, int l, int r) {
    if (l < r) {
        int m = (l + r) / 2;
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }
}
```
**Analysis Result:** Time O(log n) or O(n log n), Space O(n)

---

## 📂 Project Structure

```
java-complexity-analyzer/
│
├── ComplexityAnalyzer.java       # Console version source code
├── ComplexityAnalyzerGUI.java    # GUI version source code
├── README.md                      # This file
├── statement.md                   # Problem statement and scope
│
├── ComplexityAnalyzer.class       # Compiled console version (after compilation)
├── ComplexityAnalyzerGUI.class    # Compiled GUI version (after compilation)
│
└── docs/                          # Additional documentation (optional)
    └── Java Code Complexity Analyzer - Om Shrivastava.pdf          # Detailed project report
```

---

## 🤝 Contributing

This is an academic project for VIT Bhopal University. However, suggestions for improvements are welcome:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

---

## 📝 Known Limitations

- Analysis is based on pattern matching, not full AST parsing
- May not detect all complexity patterns in highly optimized code
- Comments and strings containing keywords might affect accuracy
- Does not analyze library function complexities in detail
- Amortized complexity is not calculated
- Best/average/worst case distinctions are not made

---

## 🔮 Future Enhancements

- Integration with Java AST parser for more accurate analysis
- Support for analyzing multiple files at once
- Export results to PDF/CSV format
- Syntax highlighting in GUI
- More sophisticated recursion analysis
- Support for other programming languages
- IDE plugin development
- Real-time analysis as user types

---

## 📄 License

This project is developed as part of academic coursework at VIT Bhopal University.

**Course:** Programming with Java (CSE2006)  
**Academic Year:** 2025-26  
**School:** School of Computing Science and Engineering (Cloud Computing)

---

## 👤 Author

**Om Shrivastava**  
Registration Number: 24BSA10362  
VIT Bhopal University  
Email: [omshrivastava01927@gmail.com]
Email: [shrivastava.24bsa10362@vitbhopal.ac.in]

---

## 🙏 Acknowledgments

- VIT Bhopal University faculty for guidance
- Course materials and references from Introduction to Algorithms (CLRS)
- Java documentation and community resources
- Open-source community for inspiration

---

## 📞 Support

For issues, questions, or suggestions:
- Open an issue in the repository
- Contact via email
- Refer to the detailed project report for more information

---

**Last Updated:** November 23, 2025
