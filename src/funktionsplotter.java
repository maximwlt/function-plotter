import java.awt.Color;
import java.util.*;
import java.util.regex.*;

import lvp.Clerk;
import lvp.skills.Text;
import lvp.skills.Interaction;
import lvp.views.Dot;
// import lvp.views.Turtle; // Es wird die veränderte Turtle.java verwendet

void main() {
    Clerk.clear();
    Clerk.markdown("""
    # Funktionsplotter Projekt in Programmieren 2 - SS25
    von: *Maxim Walter*

    ---

    # Dokumentation
    ## Inhaltsverzeichnis:
    - Einführung
        - Eingabe
        - Anzeige
        - Interna
    - 1.Erweiterung
    - 2.Erweiterung
    - 3.Erweiterung
    - Playground

    ## Einführung
    Beachten Sie die Hinweise auf der README.md Datei bei der Ausführung der LVP
    """);
    Clerk.markdown(Text.fillOut("""
    **Modifizierte Turtle.java** <br>
    Es wurden drei Methoden hinzugefügt, um das Zeichnen mit der Turtle zu erleichtern. Die Turtle.java muss im Verzeichnis enthalten bleiben

    ```java
    ${0}
    ```
    """, Text.codeBlock("./Turtle.java", "// cut-out")));
    Clerk.markdown("Im Folgenden wird die Funktionsweise des Funktionsplotters beschrieben");
    Clerk.markdown("""
    ### Eingabe <br>
    --- 
    Es gibt 4 Möglichkeiten wie ein arithmetischer Ausdruck eingegeben werden kann.
    """);
    
    // Beispielwerte
    // out example 1
    String infix_example = "2 * x ^ 3 + e^x + x / 3 - pi"; // Expression 1
    Expr infix_example_expr = Parser.parse(infix_example);
    // out example 1
    // out example 2
    String upn_example = "x 2 ^ 4 + sqrt x 3 / ln +"; // Expression 2
    Expr upn_example_expr = Parser.parse(upn_example);
    // out example 2
    // out example 3
    String infix_example_v2 = "log_3(27) + sqrt(9) * e ^ pi "; // Expression 3
    String res_infix_v2 = printExpression(infix_example_v2);
    // out example 3
    // out example 4
    String upn_example_v2 = "45 tan 16 sqrt /"; // Expression 4
    String res_upn_v2 = printExpression(upn_example_v2);
    // out example 4


    
    Clerk.markdown("*1. Der arithmetische Ausdruck, der eine Variable x enthält, kann als Infix-Notation eingegeben werden: <br>*");
    Clerk.write(Interaction.input("./funktionsplotter.java", "// Expression 1", "String infix_example = \"$\";", "Eingabe in Infix-Notation"));   
    Clerk.markdown(String.format("Es wurde eingegeben: $%s$ <br>", infix_example));
    Clerk.markdown("Der Infix-Ausdruck (mit Variable) wird vom Parser geparsed und der entsprechende AST-Baum generiert: <br>" + infix_example_expr.toString());    
    Clerk.markdown(Text.fillOut("""
    Hier der dazugehörige Codeabschnitt
    ```java
    ${0}
    ```
    """, Text.codeBlock("./funktionsplotter.java", "// out example 1")));  


    Clerk.markdown("*2. Der arithmetische Ausdruck, der eine Variable x enthält, kann als UPN-Notation eingegeben werden: <br>*");
    Clerk.write(Interaction.input("./funktionsplotter.java", "// Expression 2", "String upn_example = \"$\";", "Eingabe in UPN-Notation"));   
    Clerk.markdown(String.format("Es wurde eingegeben: %s <br>", upn_example));
    Clerk.markdown("Der UPN-Ausdruck (mit Variable) wird vom Parser geparsed und der entsprechende AST-Baum generiert: <br>" + upn_example_expr.toString());    
    Clerk.markdown(Text.fillOut("""
    Hier der dazugehörige Codeabschnitt
    ```java
    ${0}
    ```
    """, Text.codeBlock("./funktionsplotter.java", "// out example 2")));  


    Clerk.markdown("*3. Der arithmetische Ausdruck als Infix-Notation (ohne Variable) kann direkt ausgewertet werden: <br>*");
    Clerk.write(Interaction.input("./funktionsplotter.java", "// Expression 3", "String infix_example_v2 = \"$\";", "Eingabe in Infix-Notation"));   
    Clerk.markdown("Die direkte Auswertung:  " + res_infix_v2);
    // Clerk.markdown("Der Infix-Ausdruck (ohne Variable) wird vom Parser geparsed und der entsprechende AST-Baum generiert: <br>" + infix_example_expr.toString());    
    Clerk.markdown(Text.fillOut("""
    Hier der Code: <br>
    ```java
    ${0}
    ```
    Es wurde in der printExpression-Methode der String-Ausdruck geparsed, und das richtige Ergebnisformat ausgegeben (map), wenn die
    tryEval-Methode ein nicht-leeres Optional zurückgibt. Ansonsten wird der fallbackText ausgegeben.
    ```java
    ${1}
    ```
    In der tryEval-Methode wird geschaut, ob der Ausdruck eine Variable hat und entsprechend das Ergebnis als Optional
    zurückgegeben oder als leeres Optional, wenn der Ausdruck eine Variable enthält. <br>
    Der folgende Codeausschnitt ist innerhalb des AST-Sealed-Interfaces:
    ```java
    ${2}
    ```
    """, Text.codeBlock("./funktionsplotter.java", "// out example 3"),
         Text.codeBlock("./funktionsplotter.java", "// print Expression"),
         Text.codeBlock("./funktionsplotter.java", "// cut out tryEval")));


    Clerk.markdown("*4.Der arithmetische Ausdruck als UPN-Notation kann direkt ausgewertet werden: <br>*");
    Clerk.write(Interaction.input("./funktionsplotter.java", "// Expression 4", "String upn_example_v2 = \"$\";", "Eingabe in UPN-Notation"));   
    Clerk.markdown("Die direkte Auswertung:  " + res_upn_v2);

    Clerk.markdown("""
    In den 4 Beispielausdrücken wurden bereits übliche Funktionen, Konstanten etc. eines wissenschaftlichen Taschenrechners eingebunden. <br>
    Nun wird eine Übersicht gegeben der möglichen Funktionalitäten mit den verwendeten Symbolen:

    1. **Grundrechenarten**  
        - Addition: `+`  
        - Subtraktion: `-`  
        - Multiplikation: `*`  
        - Division: `/`
        - Negation: `~` (Bei Ausdrücken ohne Vorgängeroperand)

    2. **Potenz**  
        - `^`

    3. **Wurzel**  
        - `sqrt(NUM)`

    4. **Logarithmus**  
        - Logarithmus zur Basis 10 (Standard): `log(NUM)`  
        - Logarithmus mit beliebiger Basis: `log_BASE(NUM)`
        - Natürlicher Logarithmus: `ln(NUM)`

    5. **Trigonometrische Funktionen**  
        - Sinus: `sin(NUM)`
        - Kosinus: `cos(NUM)`,
        - Tangenz: `tan(NUM)`

    6. **Konstanten**  
        - Eulersche Zahl $e$: `e`
        - Kreiszahl $\\pi$ : `pi`
    7. **Sonstiges:**
        - e-Funktion $e^x$ : `exp(NUM)`
        - (Alternativ $e^x$ manuell zusammenbauen mit der Konstante $e$, jedoch ist `exp(NUM)` präziser, da es auf der Math-Library basiert) <br>

    Anmerkung: `NUM` und `BASE` müssen logischerweise bei der Angabe durch konkrete Zahlenwerte ersetzt werden

    """);

    Clerk.markdown("""
    ### Anzeige
    --- 
    """);
    
    // cut out beispiel anzeige
    // String example_expr_v3_str = "x^3 + sin(x)^(log_6(x * cos(pi)) / sqrt(4)"; // FEHLER
    String example_expr_v3_str = "~x^3 + sin(x)^log_6(x * cos(pi)) / sqrt(4)";
    String example_expr_v3_upn_str = "x 3 ^ ~ x sin x pi cos * log_6 ^ 4 sqrt / +";
    Expr example_expr_v3 = Parser.parse(example_expr_v3_str);
    Expr example_expr_v3_upn = Parser.parse(example_expr_v3_upn_str);
    ASTVisualisation ast_visualisation = new ASTVisualisation();
    String dot2_syntax = ast_visualisation.toDot(example_expr_v3);
    String dot3_syntax = ast_visualisation.toDot(example_expr_v3_upn);
    // cut out beispiel anzeige

    Clerk.markdown("Es wurden bereits im Abschnitt \"*Eingabe*\" die Ausdrücke in abstrakter Syntax angezeigt");
    Clerk.markdown("Hier ein weiteres Beispiel: " + example_expr_v3_str);
    Clerk.markdown("Der entsprechende AST-Baum: <br>" + example_expr_v3.toString());
    Clerk.markdown("Dieser AST lässt sich mithilfe der DOT-Sprache darstellen:");
    
    // display dot graph 1
    new Dot().draw(dot2_syntax);
    // display dot graph 1
    Clerk.markdown("Die gleiche Funktion nur in UPN-Schreibweise: "  + example_expr_v3_upn_str);
    Clerk.markdown(String.format("Der entsprechende AST-Baum aus dem UPN-Ausdruck: <br> %s", example_expr_v3_upn));
    Clerk.markdown("Die gleiche Funktion nur in UPN-Schreibweise generiert den gleichen DOT-Graphen: ");
    // display dot graph 2
    new Dot().draw(dot3_syntax);
    // display dot graph 2

    Clerk.markdown("Die dazugehörige generierte DOT-Syntax sieht folgendermaßen aus: <br>" + dot2_syntax);
                
    Clerk.markdown(Text.fillOut("""
    Der dazugehörige Code, der aus dem AST-Baum über die `toDot`-Methode die DOT-Syntax generiert ist folgender:
    ```java
    ${0}
    ```

    Die gesamte Ausführung für diesen Teil ist folgender:
    ```java
    ${1}
    ${2}
    ${3}
    ```
    """, Text.codeBlock("./funktionsplotter.java", "// AST Vis cut"),
         Text.codeBlock("./funktionsplotter.java", "// cut out beispiel anzeige"),
         Text.codeBlock("./funktionsplotter.java", "// display dot graph 1"),
         Text.codeBlock("./funktionsplotter.java", "// display dot graph 2")
    ));

    Clerk.markdown("Jetzt wird ausgehend von der abstrakten Syntax bzw. dem AST-Baum die Repräsentation in Infix- und UPN-Schreibweise angezeigt");
    Clerk.markdown(String.format("Es wird das vorherige Beispiel verwendet: %s <br>", example_expr_v3_str));
    Clerk.markdown(String.format("*Abstrakte Syntax:* `%s` <br>", example_expr_v3.toString()));

    // generators
    String infix_notation = InfixGenerator.generate(example_expr_v3);
    String upn_notation = UPNGenerator.generate(example_expr_v3);
    // generators

    Clerk.markdown(String.format("Aus der Abstrakten Syntax wird der Infix-Ausdruck und der UPN-Ausdruck ausgewertet. <br> *Infix:* %s <br> *UPN:* %s", infix_notation, upn_notation));
    Clerk.markdown(Text.fillOut("""
    ```java
    ${0}
    ```

    Ein Funktionsplotter (siehe Playground oder Erweiterungen) kann erstellt werden durch die Objekte der `Plotter`-Klasse. <br>
    Dabei können die Min- und Maxwerte der x-Achse und y-Achse gewählt werden

    ```java
    ${1}
    ```

    Der Plotter wird im Bereich *Playground* angezeigt
    """, Text.codeBlock("./funktionsplotter.java", "// generators"),
         Text.codeBlock("./funktionsplotter.java", "// plotter playground")
    ));

    // tokenizer example
    List<ArithmeticTokenizer.Token> tokens_example = ArithmeticTokenizer.tokenize("4 + x / log_5(3) ^ sin(x*2)");
    String tokens_example_str = tokens_example.toString();
    // tokenizer example 

    Clerk.markdown(Text.fillOut(String.format("""
    ### Interna
    --- 

    Der AST-Baum wird mit einem `sealed interface` und `record`s realisiert. Hier ist ein Teil des Codes zu sehen:
    ```java
    ${0}
    ```

    Der arithmetische Ausdruck wird von einem Tokenizer verarbeitet. <br>
    Beispiel: <br> %s 
    ```java
    ${1}
    ``` 

    In der `Parser`-Klasse werden beide Parser (UPN und Infix) kombiniert. Wenn beide Parser fehlschlagen wird eine Exception geworfen
    ```java
    ${2}
    ```

    """, tokens_example_str),
        Text.codeBlock("./funktionsplotter.java", "// cut Expr SI"),
        Text.codeBlock("./funktionsplotter.java", "// tokenizer example"),
        Text.codeBlock("./funktionsplotter.java", "// cut combined parse")
    ));





    Clerk.markdown(Text.fillOut("""
    ## 1. Erweiterung: Eingabe und Anzeige mehrerer und farblich unterschiedlicher Funktionen
    ---

    Es können mehrere Funktionsverläufe auf einem Graphen angezeigt werden, jedoch ist nicht die Farbwahl miteinbezogen.
    Mit jeder neuen Funktion wird eine neue Farbe generiert, indem der aktuelle Farbwert mit dem Goldenen Schnitt inkrementiert wird (durch Modulo im Bereich [0,1] bleiben). <br>
    Zuerst wird der aktuelle Farbton aus dem HSL-Farbraum mit einem hohen Sättigungs- und Helligkeitswert versehen, damit die Farben sich deutlich mit Kontrast unterscheiden.
    Anschließend wird die HSL-Farbe in RGB konvertiert und das berechnete Array aus dem RGB-Farben kann verwendet werden z.B. in `plotMultipleFunctions(Expr, double...)`

    ```java
    ${0}
    ```
    Die Graphanzeige kann unter dem Abschnitt `Playground` betrachtet werden.

    """, Text.codeBlock("./funktionsplotter.java", "// cut farbgen")));

    Clerk.markdown("""
    ## 2. Erweiterung: Funktionen mit Parameter $a$, deren Werte man bei der Anzeige frei wählen kann und gleichzeitig die verschiedenen Funktionsverläufe zeigt
    --- 
    """);


    Clerk.markdown(Text.fillOut("""
    Den Ausdrücken kann zusätzlich der Parameter $a$ versehen werden mit beliebig vielen Werten, indem man beliebig weitere Argumente in die `plotFunctionsWithParams` übergibt
    
    ```java
    ${0}
    ```

    Anmerkung: Im Playground sind die Parameterwerte Plotterabhängig und werden für alle verschiedenen Parameterfunktionen verwendet d.h. man kann nicht einzelnd unterschiedliche Parameterwerte für verschiedene Funktionen auswählen. Die Farben werden dort auch angezeigt
    """, Text.codeBlock("./funktionsplotter.java", "// cut out param erweiterung")
    ));

    // cut out param erweiterung
    Plotter p2 = new Plotter(-15, 15, -10, 16);
    Expr first_expr_p2 = Parser.parse("a * x^2 - a * 1/5");
    Expr second_expr_p2 = Parser.parse("a * sin(x - pi / 4) + 1");

    //p2.redraw();
    //p2.plotFunctionWithParams(first_expr_p2, "a", 2.0, 4.0, 5.0);
    //p2.plotFunctionWithParams(second_expr_p2, "a", -1.0, 3.0, 8.0, 11.0);
    //p2.startTurtle();
    // cut out param erweiterung



    Clerk.markdown("""
    ## 3. Erweiterung: Ableitungsrechner + Anzeige
    ---

    """);

    Plotter p3 = new Plotter(-15, 15, -10, 16);
    Expr normal_expr = Parser.parse("x^4 + 5*x + 9");
    Expr first_derived_expr = Differentiator.derive(normal_expr); // 4 * x^3 + 5
    Expr second_derived_expr = Differentiator.derive(first_derived_expr); // 12 * x^2
    Expr third_derived_expr = Differentiator.derive(second_derived_expr); // 24 * x

    p3.redraw();
    p3.plotFunction(normal_expr);
    p3.plotFunction(first_derived_expr);
    p3.plotFunction(second_derived_expr);
    p3.plotFunction(third_derived_expr);
    p3.startTurtle();


    Clerk.markdown("""
    ## Playground: Der Spaß fängt an!
    --- 
    """);

    Clerk.markdown(Text.fillOut("""
    **Funktionseingabe:** <br>
    Die Funktionen können in Form von Ausdrücken kommagetrennt als Strings angegeben werden.
    ```java
    ${0}
    ```
    Zusätzlich die Eingabe der Parameterwerte für `a`, die für alle Parameterfunktionen innerhalb dieses Plotters gelten: <br>
    ```java
    ${1}
    ```
    Die Grenzen des Funktionsplotter können angepasst werden:
    ```java
    ${2}
    ```

    """, Text.codeBlock("./funktionsplotter.java", "// Funktionen"),
         Text.codeBlock("./funktionsplotter.java", "// Eingabe Param"),
         Text.codeBlock("./funktionsplotter.java", "// plotter playground")
    ));





    // Funktionen
    String[] examples = {
        "sin(x) + cos(x)", // Infix mit Funktion
        "a * e^x + 6",
        "4*x^3 + log_4(x)",
        "1/x",
        "45 cos 10 * 9 sqrt +",
        "x^4 + 5*x + 9",
        "4*x^3 + 5",
        //"3 x 2 ^ * 5 -", // UPN mit Variable
        // "x sin x cos +",   // UPN mit Funktion
        // "(6 + 2) * 2", // Infix mit Klammern
        // "6 + 2 * 5",    // Infix ohne Klammern
        // "6 + (2 * 5)", // Infix mit Klammern
        // "~x+2",
        //"e^2",
        // "1/x",
        // "x^3 - 2 * x", 
        // "sin(x)*5",   
        // "ln(x)",
        // "e^x",
    };
    // Funktionen
    
    // Eingabe Param
    double[] parameterValues = {
        1.0,
        3.0,
        -1.5,
    };
    // Eingabe Param

    // plotter playground
    Plotter plotter = new Plotter(-15, 20, -10, 30); // (xMin, xMax, yMin, yMax)
    // plotter playground

    // plotter.setParam("a", 1.0);

    Map<Expr, String> exprToDot = new HashMap<>();
    Map<Expr, String> exprToOriginal = new HashMap<>();

    for (String example : examples) {
        try {
            Expr expr = Parser.parse(example);
            exprToOriginal.put(expr, example);
            ASTVisualisation ast_vis = new ASTVisualisation();
            String dot_string = ast_vis.toDot(expr);
            exprToDot.put(expr, dot_string);
            // ???

        } catch (IllegalArgumentException e) {
            Clerk.markdown("Error: " + e.getMessage());
        }
    }
    plotter.redraw();
    plotter.plotMultipleFunctions(new ArrayList<>(exprToDot.keySet()), parameterValues);
    plotter.startTurtle();

    

    int func_counter = 1;
    Clerk.markdown("### Funktionsübersicht:");
    for (Expr expr : new ArrayList<>(exprToDot.keySet())) {
        Clerk.markdown("--- ");
        int[] colorsRGB = plotter.functionColors.get(expr);
        Clerk.markdown(String.format("**<span style=\"color: rgb(%d,%d,%d)\">Funktion Nr.%s**</span>", colorsRGB[0], colorsRGB[1], colorsRGB[2], func_counter));
        // DOT-Graph Anzeige
        Clerk.markdown(String.format("**DOT-Syntax:** <br> %s", exprToDot.get(expr)));
        Clerk.markdown("**DOT-Graph:**");
        new Dot().draw(exprToDot.get(expr));

        if (!expr.containsVar() && !expr.containsParameter()) {
             // Unmittelbare Auswertung
             String originalExpr = exprToOriginal.get(expr);
             Clerk.markdown(String.format("**Direkte Auswertung:** %s", printExpression(originalExpr)));
        }
        else if (expr.containsParameter()) {
            Clerk.markdown(String.format("Benutzte Parameterwerte: %s", Arrays.toString(parameterValues)));
            Clerk.markdown("**Direkte Auswertung:** Nicht möglich, da der Ausdruck eine Variable oder Parameter enthält.");
        }
        else {
            Clerk.markdown("**Direkte Auswertung:** Nicht möglich, da der Ausdruck eine Variable enthält.");
        }
        // INFIX Ausgabe
        String infix_output = InfixGenerator.generate(expr);
        Clerk.markdown(String.format("**Infix:** %s", infix_output)); 
        // UPN Ausgabe
        String rpn_output = UPNGenerator.generate(expr);
        Clerk.markdown(String.format("**UPN:** %s", rpn_output));
        func_counter++;
    }
}


// print Expression
public String printExpression(String exprStr) {
    String fallbackText = "Keine unmittelbare Auswertung möglich";
    return Parser.parse(exprStr)
                .tryEval()
                .map(res -> String.format("%s = %.3f", exprStr, res))
                .orElse(fallbackText);  
}
// print Expression

class Plotter {
    private final double MARGIN = 1.5;
    private final double DRAW_STEPS = 0.01;
    double plotXMin, plotXMax, plotYMin, plotYMax;
    double displayXMin, displayXMax, displayYMin, displayYMax; 
    Turtle turtle;
    private final ColorGenerator colorGenerator = new ColorGenerator();
    private final Map<Expr, int[]> functionColors = new HashMap<>();
    private Map<String, Double> parameters = new HashMap<>();


    public Plotter(double xMin, double xMax, double yMin, double yMax) {
        assert xMin < xMax : "xMin darf nicht größer sein als xMax";
        assert yMin < yMax : "yMin darf nicht größer sein yMax";
        this.plotXMin = xMin;
        this.plotXMax = xMax;
        this.plotYMin = yMin;
        this.plotYMax = yMax;
        
        updateDisplayBounds();
        
        turtle = new Turtle(displayXMin, displayXMax, displayYMin, displayYMax, (plotXMin + plotXMax)/2, (plotYMin + plotYMax)/2, 0);
        turtle.color(0, 0, 0).width(0.1);
    }

    private void updateDisplayBounds() {
        this.displayXMin = plotXMin - MARGIN;
        this.displayXMax = plotXMax + MARGIN;
        this.displayYMin = plotYMin - MARGIN;
        this.displayYMax = plotYMax + MARGIN;
    }


    void draw_coordinate_axes() {
        // Rahmen für den Plotbereich
        turtle.color(150, 150, 150).width(0.125);
        turtle.penUp().moveTo(plotXMin, plotYMin).penDown();
        turtle.moveTo(plotXMax, plotYMin);
        turtle.moveTo(plotXMax, plotYMax);
        turtle.moveTo(plotXMin, plotYMax);
        turtle.moveTo(plotXMin, plotYMin);
        
        // Hilfslinien (grau)
        turtle.color(200, 200, 200).width(0.05);
        
        // Vertikale Hilfslinien
        for (double x = Math.ceil(plotXMin); x <= Math.floor(plotXMax); x++) {
            turtle.penUp().moveTo(x, plotYMin).penDown().moveTo(x, plotYMax);
                
            // Beschriftung  X-Achse
            turtle.penUp().moveTo(x, plotYMin).setAngle(265); // Zeigt nach unten
            turtle.forward(1.75); // Etwas Abstand von der Achse
            turtle.text(String.valueOf((int) x), "0.5px sans-serif");
        }
        
        // Horizontale Hilfslinien
        for (double y = Math.ceil(plotYMin); y <= Math.floor(plotYMax); y++) {
            turtle.penUp().moveTo(plotXMin, y).penDown().moveTo(plotXMax, y);
                
            // Beschriftung links der Y-Achse
            turtle.penUp().moveTo(plotXMin, y).setAngle(240); // Zeigt nach links
            turtle.forward(1.275); // Etwas Abstand von der Achse
            turtle.text(String.valueOf((int) y), "0.5px sans-serif");
        }
        // Hauptachsen (schwarz)
        turtle.color(0, 0, 0);
        turtle.width(0.075);
        
        // X-Achse (nur innerhalb des Plotbereichs zeichnen)
        turtle.penUp().moveTo(plotXMin, 0).penDown().moveTo(plotXMax, 0);
        
        // Y-Achse (nur innerhalb des Plotbereichs zeichnen)
        turtle.penUp().moveTo(0, plotYMin).penDown().moveTo(0, plotYMax);
    }

    void plotFunction(Expr expr) {
        plotFunctionCustom(expr, plotXMin, plotXMax);
    }


    void plotFunctionWithParams(Expr expr, String p, double... params) {
        Arrays.stream(params).forEach(par -> {
            setParam(p, par);
            plotFunction(expr);
        });
    }

    void plotFunctionCustom(Expr expr, double xMin, double xMax) {
        turtle.width(0.075); // Liniendicke
        boolean penDown = false;
        double lastValidX = xMin;
        double lastValidY = 0;
        double stepSize = DRAW_STEPS;

        for (double x = xMin; x <= xMax; x += stepSize) {
            try {
                double y = eval(expr, x);

                // Prüfe ob Punkt innerhalb des definierten Bereichs liegt
                boolean isInBounds = y >= plotYMin && y <= plotYMax;
                boolean isValid = !Double.isNaN(y) && !Double.isInfinite(y);

                if (isValid && isInBounds) {
                    if (!penDown) {
                        // Neuen Abschnitt beginnen
                        turtle.penUp().moveTo(x, y).penDown();
                        penDown = true;
                    } else {
                        // Prüfe auf Diskontinuität (große Sprünge)
                        double deltaX = x - lastValidX;
                        double deltaY = Math.abs(y - lastValidY);
                        
                        double yRange = plotYMax - plotYMin;
                        double discontinuityThreshold = yRange * 0.2;

                        if (deltaY > discontinuityThreshold && deltaX > DRAW_STEPS * 2) {
                            // Linie unterbrechen bei großen Sprüngen
                            turtle.penUp().moveTo(x, y).penDown();
                        } else {
                            turtle.moveTo(x, y);
                        }
                    }
                    lastValidX = x;
                    lastValidY = y;
                } else {
                    // Punkt außerhalb des Bereichs -> Stift anheben
                    penDown = false;
                }
            } catch (ArithmeticException e) {
                penDown = false;
            }
        }
    }


    void plotMultipleFunctions(List<Expr> functions) {
        functions.forEach(function -> {
            int[] color = functionColors.computeIfAbsent(function, e -> colorGenerator.getNextColor());
            turtle.color(color[0], color[1], color[2]);
            plotFunction(function);
        });
    }

    void plotMultipleFunctions(List<Expr> functions, double... params) {
        functions.forEach(function -> {
            int[] color = functionColors.computeIfAbsent(function, e -> colorGenerator.getNextColor());
            functionColors.put(function, color);
            turtle.color(color[0], color[1], color[2]);

            if (function.containsParameter()) {
                plotFunctionWithParams(function, "a", params);
            }
            else {
                plotFunction(function);
            }
        });
    }

    public void setParam(String name, double value) {
        parameters.put(name, value);
    }

    double eval(Expr expr, double x) {
        return switch(expr) {
            case Expr.Number num -> num.value();
            case Expr.Variable v -> x;
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e)-> {
                double value = eval(e, x);
                yield switch (op) {
                    case NEG -> -value;
                    default -> throw new UnsupportedOperationException();
                };
            }
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> {
                double left = eval(l, x);
                double right = eval(r, x);
                yield switch(op) {
                    case ADD -> left + right;
                    case SUB -> left - right;
                    case MUL -> left * right;
                    case DIV -> left / right;
                    case POW -> Math.pow(left, right);
                    default -> throw new UnsupportedOperationException();
                };
            }
            case Expr.FunctionCall(ArithmeticTokenizer.Token.Function func, Expr arg) -> {
                double value = eval(arg, x);
                yield switch(func) {
                    case SIN -> Math.sin(value);
                    case COS -> Math.cos(value);
                    case TAN -> Math.tan(value);
                    case LOG -> Math.log10(value);
                    case LN -> Math.log(value);
                    case SQRT -> Math.sqrt(value);
                    case EXP -> Math.exp(value);
                    default -> throw new UnsupportedOperationException();
                };
            }
            case Expr.Constant(ArithmeticTokenizer.Token.Constant c) -> switch (c) {
                case PI -> Math.PI;
                case E -> Math.E;
            };
            case Expr.LogBase(Double base, Expr arg) -> {
                double value = eval(arg, x);
                yield Math.log(value) / Math.log(base);
            }
            case Expr.Parameter p -> {
                Double val = parameters.get(p.name());
                if (val == null) throw new ArithmeticException("Parameter " + p.name() + " ist nicht gesetzt");
                yield val;
            }
        };
    }

    Turtle redraw() {
        turtle.penUp().moveTo(0, 0).setAngle(0); // Turtle zurücksetzen
        draw_coordinate_axes(); // Koordinatensystem zeichnen
        return turtle;
    }

    Turtle startTurtle() {
        Clerk.markdown("### Funktionsgraph: ");
        this.turtle = turtle.write();
        return turtle;
    }

}
// cut farbgen
// ================= Farbgenerator =================
class ColorGenerator {
    private static final float GOLDEN_NUM = 1.618033988749895f;
    private float hue = 0;
    
    public int[] getNextColor() {
        hue += GOLDEN_NUM;
        hue %= 1; // Bleibt im Bereich [0,1]
        
        // HSB zu RGB: Sättigung 90%, Helligkeit 95%
        int rgb = Color.HSBtoRGB(hue, 0.9f, 0.95f);
        return new int[] {
            (rgb >> 16) & 0xFF, // Rot
            (rgb >> 8) & 0xFF,  // Grün
            rgb & 0xFF          // Blau
        };
    }
}
// cut farbgen



// ================= Token und Tokenizer =================
class ArithmeticTokenizer {
    
    public sealed interface Token permits Token.Number, Token.Operator, Token.Paren, Token.Variable, Token.Function, Token.Constant, Token.Illegal, Token.LogBase, Token.Parameter {
        
        record Number(double value) implements Token {}
        
        enum Operator implements Token { 
            ADD("+"),
            SUB("-"),
            MUL("*"),
            DIV("/"),
            NEG("~"),
            POW("^");
            final String symbol;
            Operator(String symbol) { this.symbol = symbol; }
        }
        
        enum Paren implements Token { OPEN("("), CLOSE(")"); 
            final String symbol;
            Paren(String symbol) { this.symbol = symbol; }
        }
        
        record Variable(String name) implements Token {}
        
        enum Function implements Token { 
            SIN("sin"),
            COS("cos"),
            TAN("tan"), 
            LOG("log"),
            LN("ln"),
            SQRT("sqrt"),
            EXP("exp");
            final String name;
            Function(String name) { this.name = name; }
        }
        
        enum Constant implements Token {
            PI("pi"), E("e");
            final String symbol;
            Constant(String symbol) { this.symbol = symbol; }
        }
        
        record LogBase(double base) implements Token {}
        record Illegal(String text) implements Token {}

        record Parameter(String name) implements Token {}
    }

    public static List<Token> tokenize(String input) {
        return new Tokenizer(input).tokenize();
    }

    private static class Tokenizer {
        private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "(?<NUM>\\d+\\.?\\d*)|" +      // Zahlen
            "(?<FUNC>sin|cos|tan|log_\\d+|log|ln|sqrt|exp)|" + // Funktionen
            "(?<CONST>pi|e)|" +             // Konstanten
            "(?<PAR>a)|" +                // Parameter
            "(?<VAR>[b-zB-Z][b-zB-Z0-9]*)|" + // Variablen
            "(?<OP>[+\\-*/~^])|" +         // Operatoren
            "(?<PAREN>[()])|" +            // Klammern
            "(?<WS>\\s+)|" +               // Whitespace (ignoriert)
            "(?<ILLEGAL>.)"                // Ungültige Zeichen
        );

        private final String input;
        private final Matcher matcher;

        Tokenizer(String input) {
            this.input = input;
            this.matcher = TOKEN_PATTERN.matcher(input);
        }

        List<Token> tokenize() {
            return matcher.results()
                .<Token>mapMulti((result, consumer) -> {
                    if (result.group("NUM") != null) {
                        consumer.accept(new Token.Number(
                            Double.parseDouble(result.group())
                        ));
                    } 
                    else if (result.group("OP") != null) {
                        consumer.accept(switch (result.group()) {
                            case "+" -> Token.Operator.ADD;
                            case "-" -> Token.Operator.SUB;
                            case "*" -> Token.Operator.MUL;
                            case "/" -> Token.Operator.DIV;
                            case "~" -> Token.Operator.NEG;
                            case "^" -> Token.Operator.POW;
                            default -> throw new IllegalStateException();
                        });
                    }
                    else if (result.group("PAREN") != null) {
                        consumer.accept(switch (result.group()) {
                            case "(" -> Token.Paren.OPEN;
                            case ")" -> Token.Paren.CLOSE;
                            default -> throw new IllegalStateException();
                        });
                    }
                    else if (result.group("VAR") != null) {
                        consumer.accept(new Token.Variable(result.group()));
                    }
                    else if (result.group("FUNC") != null) {
                        consumer.accept(switch (result.group()) {
                            case "sin" -> Token.Function.SIN;
                            case "cos" -> Token.Function.COS;
                            case "tan" -> Token.Function.TAN;
                            case String s when s.startsWith("log_") -> {
                                String base_str = s.substring(4);
                                try {
                                    double base = Double.parseDouble(base_str);
                                    yield new Token.LogBase(base);
                                } catch (NumberFormatException nfe) {
                                    yield new Token.Illegal(s);
                                }
                            }
                            case "log" -> Token.Function.LOG;
                            case "ln" -> Token.Function.LN;
                            case "sqrt" -> Token.Function.SQRT;
                            case "exp" -> Token.Function.EXP;
                            default -> throw new IllegalStateException();
                        });
                    }
                    else if (result.group("CONST") != null) {
                        consumer.accept(switch (result.group()) {
                            case "pi" -> Token.Constant.PI;
                            case "e" -> Token.Constant.E;
                            default -> throw new IllegalStateException();
                        });
                    }
                    else if (result.group("ILLEGAL") != null) {
                        consumer.accept(new Token.Illegal(result.group()));
                    }
                    else if (result.group("PAR") != null) {
                        consumer.accept(new Token.Parameter(result.group()));
                    }
                    // Whitespace wird ignoriert
                })
                .toList();
        }
    }
}

// ================= AST (Abstrakter Syntaxbaum) =================
// cut Expr SI
sealed interface Expr permits Expr.Number, Expr.Variable, Expr.UnaryOp, Expr.BinaryOp, Expr.FunctionCall, Expr.Constant, Expr.LogBase, Expr.Parameter {
    record Number(double value) implements Expr {}
    record Variable(String name) implements Expr {}
    record UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr operand) implements Expr {}
    record BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr left, Expr right) implements Expr {}
    record FunctionCall(ArithmeticTokenizer.Token.Function function, Expr argument) implements Expr {}
    record Constant(ArithmeticTokenizer.Token.Constant constant) implements Expr {}
    record LogBase(double base, Expr expr) implements Expr {}
    record Parameter(String name) implements Expr {}
    // (...)
// cut Expr SI    
    Map<String, Double> parameters = new HashMap<>();

    // cut out tryEval
    default boolean containsVar() {
        return switch(this) {
            case Expr.Variable v -> true;
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> e.containsVar();
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> l.containsVar() || r.containsVar();
            case Expr.FunctionCall(ArithmeticTokenizer.Token.Function f, Expr arg) -> arg.containsVar();
            case Expr.LogBase(double base, Expr arg) -> arg.containsVar();
            default -> false;
        };
    }

    default Optional<Double> tryEval() {
        return this.containsVar() ?  Optional.empty() : Optional.of(eval(0)); // Wert für x ist hier irrelevant, da sowieso keine Variable gegeben ist und das Ergebnis konstant gleich bleibt
    }
    // cut out tryEval

    default boolean containsParameter() {
        return switch(this) {
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> e.containsParameter();
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> l.containsParameter() || r.containsParameter();
            case Expr.FunctionCall(ArithmeticTokenizer.Token.Function f, Expr arg) -> arg.containsParameter();
            case Expr.LogBase(double base, Expr arg) -> arg.containsParameter();
            case Expr.Parameter p -> true;
            default -> false;
        };
    }

    default void setParam(String name, double val) {
        parameters.put(name, val);
    }

    
    private double eval(double x) {
        return switch(this) {
            case Number n -> n.value();
            case Variable v -> x;
            case UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> switch(op) {
                case NEG -> -e.eval(x);
                default -> throw new UnsupportedOperationException();
            };
            case BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> switch(op) {
                case ADD -> l.eval(x) + r.eval(x);
                case SUB -> l.eval(x) - r.eval(x);
                case MUL -> l.eval(x) * r.eval(x);
                case DIV -> l.eval(x) / r.eval(x);
                case POW -> Math.pow(l.eval(x), r.eval(x));
                default -> throw new UnsupportedOperationException();
            };
            case FunctionCall(ArithmeticTokenizer.Token.Function func, Expr arg) -> switch(func) {
                case SIN -> Math.sin(arg.eval(x));
                case COS -> Math.cos(arg.eval(x));
                case TAN -> Math.tan(arg.eval(x));
                case LOG -> Math.log10(arg.eval(x));
                case LN -> Math.log(arg.eval(x));
                case SQRT -> Math.sqrt(arg.eval(x));
                case EXP -> Math.exp(arg.eval(x));
            };
            case Constant(ArithmeticTokenizer.Token.Constant c) -> switch(c) {
                case PI -> Math.PI;
                case E -> Math.E;
            };
            case LogBase(Double base, Expr arg) -> Math.log(arg.eval(x)) / Math.log(base);
            case Parameter p -> { 
                Double value = parameters.get(p.name());
                if (value == null) throw new ArithmeticException("Parameter '" + p.name() + "' not set");            
                yield value;
            }
        };
    }

    
}


// AST Vis cut
class ASTVisualisation {
    private final StringBuilder dot_graph = new StringBuilder();
    private final Map<Expr, String> node_names = new HashMap<>();
    private int counter = 0;

    public String toDot(Expr expr) {
        dot_graph.append("digraph AST {\n");
        traverse(expr);
        dot_graph.append("}\n");
        return dot_graph.toString();
    }

    public String traverse(Expr expr) {
        String nodeID = "Node" + counter++;
        node_names.put(expr, nodeID);

        switch (expr) {
            case Expr.Number num -> dot_graph.append("  ").append(nodeID).append(" [label=\"").append(num.value()).append("\"];\n");
            case Expr.Variable variable -> dot_graph.append("  ").append(nodeID).append(" [label=\"").append(variable.name()).append("\"];\n");
            case Expr.Constant cons -> dot_graph.append("  ").append(nodeID).append(" [label=\"").append(cons.constant()).append("\"];\n");
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> {
                dot_graph.append("  ").append(nodeID).append(" [label=\"").append(op.symbol).append("\"];\n");
                String child_name = traverse(e);
                dot_graph.append("  ").append(nodeID).append(" -> ").append(child_name).append(";\n");
            }
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> {
                dot_graph.append("  ").append(nodeID).append(" [label=\"").append(op.symbol).append("\"];\n");
                String leftId = traverse(l);
                String rightId = traverse(r);
                dot_graph.append("  ").append(nodeID).append(" -> ").append(leftId).append(";\n");
                dot_graph.append("  ").append(nodeID).append(" -> ").append(rightId).append(";\n");
            }
            case Expr.FunctionCall(ArithmeticTokenizer.Token.Function func, Expr arg) -> {
                dot_graph.append("  ").append(nodeID).append(" [label=\"").append(func.name()).append("\"];\n");
                String argId = traverse(arg);
                dot_graph.append("  ").append(nodeID).append(" -> ").append(argId).append(";\n");
            }
            case Expr.LogBase(Double base, Expr arg) -> {
                dot_graph.append("  ").append(nodeID).append(" [label=\"log_").append(base).append("\"];\n");
                String argId = traverse(arg);
                dot_graph.append("  ").append(nodeID).append(" -> ").append(argId).append(";\n");
            }
            case Expr.Parameter p -> dot_graph.append("  ").append(nodeID).append(" [label=\"").append(p.name()).append("\"];\n");

        }
        return nodeID;
    }
}
// AST Vis cut



// ================= Parser für Infix-Notation =================
class InfixParser {
    public static Expr parse(List<ArithmeticTokenizer.Token> tokens) {
        Deque<Expr> output = new ArrayDeque<>();
        Deque<ArithmeticTokenizer.Token> operators = new ArrayDeque<>();

        // Clerk.markdown("### Infix Parsing Steps");
        
        for (ArithmeticTokenizer.Token token : tokens) {
            // Clerk.markdown("**Next Token:** " + token);
            switch (token) {
                case ArithmeticTokenizer.Token.Number n -> {
                    output.push(new Expr.Number(n.value()));
                    // printStack(output, "Output");
                }
                case ArithmeticTokenizer.Token.Variable v -> {
                    output.push(new Expr.Variable(v.name()));
                    // printStack(output, "Output");
                }
                case ArithmeticTokenizer.Token.Constant c -> {
                    output.push(new Expr.Constant(c));
                    // printStack(output, "Output");
                }
                case ArithmeticTokenizer.Token.Paren p -> {
                    if (p == ArithmeticTokenizer.Token.Paren.OPEN) {
                        operators.push(p);
                    } else {
                        while (!operators.isEmpty() && operators.peek() != ArithmeticTokenizer.Token.Paren.OPEN) {
                            processOperator(output, operators.pop());
                            // printStack(output, "Output");
                        }
                        operators.pop(); // Öffnende Klammer entfernen

                        // Direkte Verarbeitung von Funktionen
                        if (!operators.isEmpty() &&
                                (operators.peek() instanceof ArithmeticTokenizer.Token.Function ||
                                 operators.peek() instanceof ArithmeticTokenizer.Token.LogBase)) {
                            processOperator(output, operators.pop());
                            // printStack(output, "Output");
                        }
                    }
                    // printStack(operators, "Operators");
                }
                case ArithmeticTokenizer.Token.Operator op -> {
                    while (!operators.isEmpty() && shouldProcessOperator(operators.peek(), op)) {
                        processOperator(output, operators.pop());
                        // printStack(output, "Output");
                    }
                    operators.push(op);
                    // printStack(operators, "Operators");
                }
                case ArithmeticTokenizer.Token.Function f -> {
                    operators.push(f);
                    // printStack(operators, "Operators");
                }
                case ArithmeticTokenizer.Token.LogBase log_base -> {
                    operators.push(log_base);
                    // printStack(operators, "Operators");
                }
                case ArithmeticTokenizer.Token.Parameter par -> {
                    output.push(new Expr.Parameter(par.name()));
                    // printStack(output, "Output");
                }
                default -> throw new IllegalArgumentException("Unexpected token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            processOperator(output, operators.pop());
            // printStack(output, "Output");
        }

        if (output.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return output.pop();
    }


    // ################# DEBUGGING METHODEN START #################

    // private static void printStack(Deque<?> stack, String name) {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append(name).append(" Stack: [");
    //     for (Object item : stack) {
    //         sb.append(itemToString(item)).append(", ");
    //     }
    //     if (!stack.isEmpty()) {
    //         sb.setLength(sb.length() - 2); // ", " entfernen
    //     }
    //     sb.append("]");
    //     Clerk.markdown(sb.toString());
    // }

    // private static String itemToString(Object item) {
    //     if (item instanceof Expr expr) {
    //         return exprToString(expr);
    //     } else if (item instanceof ArithmeticTokenizer.Token token) {
    //         return tokenToString(token);
    //     }
    //     return item.toString();
    // }

    // private static String tokenToString(ArithmeticTokenizer.Token token) {
    //     return switch (token) {
    //         case ArithmeticTokenizer.Token.Number n -> String.valueOf(n.value());
    //         case ArithmeticTokenizer.Token.Variable v -> v.name();
    //         case ArithmeticTokenizer.Token.Constant c -> c.symbol;
    //         case ArithmeticTokenizer.Token.Operator op -> op.symbol;
    //         case ArithmeticTokenizer.Token.Paren p -> p.symbol;
    //         case ArithmeticTokenizer.Token.Function f -> f.name();
    //         default -> token.toString();
    //     };
    // }

    // private static String exprToString(Expr expr) {
    //     return switch (expr) {
    //         case Expr.Number n -> String.valueOf(n.value());
    //         case Expr.Variable v -> v.name();
    //         case Expr.Constant c -> c.constant().symbol;
    //         case Expr.UnaryOp(var op, var e) -> op.symbol + "(" + exprToString(e) + ")";
    //         case Expr.BinaryOp(var op, var l, var r) -> 
    //             "(" + exprToString(l) + " " + op.symbol + " " + exprToString(r) + ")";
    //         case Expr.FunctionCall(var f, var arg) -> f.name() + "(" + exprToString(arg) + ")";
    //         case Expr.LogBase(var base, var e) -> "log_" + base + "(" + exprToString(e) + ")";
    //         case Expr.Parameter p -> p.name();
    //     };
    // }
    // ################  DEBUGGING METHODEN END ###################
    
    private static boolean shouldProcessOperator(ArithmeticTokenizer.Token top, ArithmeticTokenizer.Token.Operator current) {
        if (top instanceof ArithmeticTokenizer.Token.Paren) return false;
            
        if (top instanceof ArithmeticTokenizer.Token.Operator topOp) {
            int topPrec = precedence(topOp);
            int currPrec = precedence(current);
            return topPrec > currPrec || (topPrec == currPrec && associativity(current) == Assoc.LEFT);
        }
        return false;
    }
    
    private static int precedence(ArithmeticTokenizer.Token.Operator op) {
        return switch (op) {
            case POW -> 4;
            case NEG -> 3;
            case MUL, DIV -> 2;
            case ADD, SUB -> 1;
        };
    }
    
    private enum Assoc { LEFT, RIGHT }
    
    private static Assoc associativity(ArithmeticTokenizer.Token.Operator op) {
        return op == ArithmeticTokenizer.Token.Operator.POW ? Assoc.RIGHT : Assoc.LEFT;
    }
    
    private static void processOperator(Deque<Expr> output, ArithmeticTokenizer.Token op) {
        switch (op) {
            case ArithmeticTokenizer.Token.Operator operator -> {
                if (operator == ArithmeticTokenizer.Token.Operator.NEG) {
                    Expr operand = output.pop();
                    output.push(new Expr.UnaryOp(operator, operand));
                }
                else {
                    Expr right = output.pop();
                    Expr left = output.pop();
                    output.push(new Expr.BinaryOp(operator, left, right));
                }
            }
            case ArithmeticTokenizer.Token.Function function -> {
                Expr arg = output.pop();
                output.push(new Expr.FunctionCall(function, arg));
            }
            case ArithmeticTokenizer.Token.LogBase log_base -> {
                Expr arg = output.pop();
                output.push(new Expr.LogBase(log_base.base(), arg)); 
            }
            default -> throw new IllegalArgumentException("Unexpected operator: " + op);
        }

        
    }
}

// ================= Parser für UPN-Notation =================
class UPNParser {
    public static Expr parse(List<ArithmeticTokenizer.Token> tokens) {
        Deque<Expr> stack = new ArrayDeque<>();
        // Clerk.markdown("### UPN Parsing Steps");
        for (ArithmeticTokenizer.Token token : tokens) {
            // Clerk.markdown("**Next Token:** " + token);
            switch (token) {
                case ArithmeticTokenizer.Token.Number n -> {
                    stack.push(new Expr.Number(n.value())); // printStack(stack);
                }
                case ArithmeticTokenizer.Token.Variable v -> {
                    stack.push(new Expr.Variable(v.name())); // printStack(stack);
                }                       
                case ArithmeticTokenizer.Token.Constant c -> {
                    stack.push(new Expr.Constant(c)); // printStack(stack);
                }
                case ArithmeticTokenizer.Token.Operator op -> {
                    if (op == ArithmeticTokenizer.Token.Operator.NEG) {
                        Expr operand = stack.pop();
                        stack.push(new Expr.UnaryOp(op, operand));
                    } else {
                        Expr right = stack.pop();
                        Expr left = stack.pop();
                        stack.push(new Expr.BinaryOp(op, left, right));
                    }
                    // printStack(stack);
                }
                case ArithmeticTokenizer.Token.Function f -> {
                    Expr arg = stack.pop();
                    stack.push(new Expr.FunctionCall(f, arg));
                    // printStack(stack);
                }
                case ArithmeticTokenizer.Token.LogBase log_base -> {
                    Expr arg = stack.pop();
                    stack.push(new Expr.LogBase(log_base.base(), arg));
                }
                case ArithmeticTokenizer.Token.Parameter p -> {
                    stack.push(new Expr.Parameter(p.name()));
                }
                default -> throw new IllegalArgumentException("Invalid UPN token: " + token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid UPN expression");
        }
        
        return stack.pop();
    }
    public static void printStack(Deque<Expr> stk) {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack: [");
        for (Expr expr : stk) {
            sb.append(exprToString(expr)).append(", ");
        }
        if (!stk.isEmpty()) {
            sb.setLength(sb.length() - 2); 
        }
        sb.append("]");
        Clerk.markdown(sb.toString());
    }
    private static String exprToString(Expr expr) {
        return switch (expr) {
            case Expr.Number n -> String.valueOf(n.value());
            case Expr.Variable v -> v.name();
            case Expr.Constant c -> c.constant().symbol;
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> op.symbol + "(" + exprToString(e) + ")";
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> 
                "(" + exprToString(l) + " " + op.symbol + " " + exprToString(r) + ")";
            case Expr.FunctionCall(ArithmeticTokenizer.Token.Function f, Expr arg) -> f.name() + "(" + exprToString(arg) + ")";
            case Expr.LogBase(Double base, Expr e) -> "log_" + base + "(" + exprToString(e) + ")";
            case Expr.Parameter p -> p.name();  
        };
    }
}

// ================= Kombinierter Parser =================
class Parser {
    // cut combined parse 
    public static Expr parse(String input) {
        List<ArithmeticTokenizer.Token> tokens = ArithmeticTokenizer.tokenize(input);
        // Zuerst UPN versuchen
        try {
            validateUPN(tokens);
            return UPNParser.parse(tokens);
        } catch (IllegalArgumentException e1) {
            // Falls UPN fehlschlägt, Infix versuchen
            try {
                return InfixParser.parse(tokens);
            } catch (IllegalArgumentException e2) {
                // UPN und Infix schlägt fehl
                throw new IllegalArgumentException(
                    "Weder gültige UPN noch Infix Notation\n" +
                    "UPN Fehler: " + e1.getMessage() + "\n" +
                    "Infix Fehler: " + e2.getMessage());
            }
        }
    }
    // cut combined parse 
    
    private static void validateUPN(List<ArithmeticTokenizer.Token> tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        
        for (ArithmeticTokenizer.Token token : tokens) {
            if (isOperand(token)) {
                stack.push(1);
            } 
            else if (isOperator(token)) {
                if (stack.size() < requiredOperands((ArithmeticTokenizer.Token.Operator) token)) {
                    throw new IllegalArgumentException(
                        "Nicht genug Operanden für Operator " + token);
                }
                int operands = requiredOperands((ArithmeticTokenizer.Token.Operator) token);
                for (int i = 0; i < operands; i++) stack.pop();
                stack.push(1);
            } 
            else if (token instanceof ArithmeticTokenizer.Token.Function) {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException(
                        "Nicht genug Operanden für Funktion " + token);
                }
                stack.pop();
                stack.push(1);
            } 
            else if (!(token instanceof ArithmeticTokenizer.Token.Illegal)) {
                throw new IllegalArgumentException("Ungültiges UPN Token: " + token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException(
                "Ungültige UPN Expression - Stack hat " + stack.size() + " Elemente");
        }
    }
    
    private static boolean isOperand(ArithmeticTokenizer.Token token) {
        return token instanceof ArithmeticTokenizer.Token.Number ||
               token instanceof ArithmeticTokenizer.Token.Variable ||
               token instanceof ArithmeticTokenizer.Token.Constant ||
               token instanceof ArithmeticTokenizer.Token.Parameter;
    }
    
    private static boolean isOperator(ArithmeticTokenizer.Token token) {
        return token instanceof ArithmeticTokenizer.Token.Operator;
    }
    
    private static int requiredOperands(ArithmeticTokenizer.Token.Operator op) {
        return op == ArithmeticTokenizer.Token.Operator.NEG ? 1 : 2;
    }

}

// ================= Infix-Generator =================
class InfixGenerator {
    public static String generate(Expr expr) {
        return generate(expr, false);
    }
    
    private static String generate(Expr expr, boolean needsParens) {
        return switch (expr) {
            case Expr.Number n -> String.valueOf(n.value());
            case Expr.Variable v -> v.name();
            case Expr.Constant c -> c.constant().symbol;
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> 
                op.symbol + generate(e, true);
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> {
                String result = generate(l, true) + " " + op.symbol + " " + generate(r, true);
                // yield needsParens ? "(" + result + ")" : result;
                yield result;
            }
            case Expr.FunctionCall(ArithmeticTokenizer.Token.Function f, Expr arg) -> 
                f.name() + "(" + generate(arg) + ")";
            case Expr.LogBase(Double base, Expr arg) -> 
                "log_" + base + "(" + generate(arg) + ")";
            case Expr.Parameter p -> p.name();
        };
    }
}

// ================= UPN-Generator =================
class UPNGenerator {
    public static String generate(Expr expr) {
        StringBuilder sb = new StringBuilder();
        generate(expr, sb);
        return sb.toString().trim();
    }
    
    private static void generate(Expr expr, StringBuilder sb) {
        switch (expr) {
            case Expr.Number n -> sb.append(n.value()).append(" ");
            case Expr.Variable v -> sb.append(v.name()).append(" ");
            case Expr.Constant c -> sb.append(c.constant().symbol).append(" ");
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> {
                generate(e, sb);
                sb.append(op.symbol).append(" ");
            }
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> {
                generate(l, sb);
                generate(r, sb);
                sb.append(op.symbol).append(" ");
            }
            case Expr.FunctionCall(ArithmeticTokenizer.Token.Function f, Expr arg) -> {
                generate(arg, sb);
                sb.append(f.name()).append(" ");
            }
            case Expr.LogBase(Double base, Expr arg) -> {
                generate(arg, sb);
                sb.append("log_").append(base).append(" ");
            }
            case Expr.Parameter p -> sb.append(p.name()).append(" ");
        }
    }
}


class Differentiator {

    public static Expr derive(Expr expr) {
        return switch (expr) {
            case Expr.Number n -> new Expr.Number(0);
            case Expr.Variable v -> new Expr.Number(1);
            case Expr.Constant(ArithmeticTokenizer.Token.Constant constant) -> {
                yield switch(constant) {
                    case PI -> new Expr.Number(0);
                    case E -> new Expr.Number(0);
                };
            }
            case Expr.UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> {
                yield switch(op) {
                    case NEG -> new Expr.UnaryOp(ArithmeticTokenizer.Token.Operator.NEG, derive(e));
                    default -> throw new UnsupportedOperationException();
                };
            }
            case Expr.BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> {
                yield switch(op) {
                    case ADD -> new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.ADD, derive(l), derive(r));
                    case SUB -> new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.SUB, derive(l), derive(r));
                    // Produktregel: (u * v)' => u' * v + u * v'
                    case MUL -> new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.ADD, 
                        new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.MUL, derive(l), r),
                        new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.MUL, l, derive(r))
                    );
                    // Quotientenregel: (u / v)' => (u' * v - u * v') / (v * v)
                    case DIV -> new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.DIV,
                        new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.SUB,
                            new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.MUL, derive(l), r),
                            new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.MUL, l, derive(r))
                        ),
                        new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.MUL, r, r)
                    );
                    // Potenzregel: x^n => n * x^(n-1)
                    case POW -> {
                        if (r instanceof Expr.Number n) {
                            yield new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.MUL, 
                                new Expr.Number(n.value()),
                                new Expr.BinaryOp(ArithmeticTokenizer.Token.Operator.POW,
                                    l,
                                    new Expr.Number(n.value() - 1)
                                )
                            );
                        } else {
                            throw new UnsupportedOperationException("Nur x^n unterstützt.");
                        }
                    }
                    default -> throw new UnsupportedOperationException("Operator is not implemented yet.");
                };
            }
            default -> throw new UnsupportedOperationException("Coming soon");
            
        };
    }
}
    /*
    record Number(double value) implements Expr {}
    record Variable(String name) implements Expr {}
    record UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr operand) implements Expr {}
    record BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr left, Expr right) implements Expr {}
    record FunctionCall(ArithmeticTokenizer.Token.Function function, Expr argument) implements Expr {}
    record Constant(ArithmeticTokenizer.Token.Constant constant) implements Expr {}
    record LogBase(double base, Expr expr) implements Expr {}
    record Parameter(String name) implements Expr {}

        private double eval(double x) {
        return switch(this) {
            case Number n -> n.value();
            case Variable v -> x;
            case UnaryOp(ArithmeticTokenizer.Token.Operator op, Expr e) -> switch(op) {
                case NEG -> -e.eval(x);
                default -> throw new UnsupportedOperationException();
            };
            case BinaryOp(ArithmeticTokenizer.Token.Operator op, Expr l, Expr r) -> switch(op) {
                case ADD -> l.eval(x) + r.eval(x);
                case SUB -> l.eval(x) - r.eval(x);
                case MUL -> l.eval(x) * r.eval(x);
                case DIV -> l.eval(x) / r.eval(x);
                case POW -> Math.pow(l.eval(x), r.eval(x));
                default -> throw new UnsupportedOperationException();
            };
            case FunctionCall(ArithmeticTokenizer.Token.Function func, Expr arg) -> switch(func) {
                case SIN -> Math.sin(arg.eval(x));
                case COS -> Math.cos(arg.eval(x));
                case TAN -> Math.tan(arg.eval(x));
                case LOG -> Math.log10(arg.eval(x));
                case LN -> Math.log(arg.eval(x));
                case SQRT -> Math.sqrt(arg.eval(x));
                case EXP -> Math.exp(arg.eval(x));
            };
            case Constant(ArithmeticTokenizer.Token.Constant c) -> switch(c) {
                case PI -> Math.PI;
                case E -> Math.E;
            };
            case LogBase(Double base, Expr arg) -> Math.log(arg.eval(x)) / Math.log(base);
            case Parameter p -> { 
                Double value = parameters.get(p.name());
                if (value == null) throw new ArithmeticException("Parameter '" + p.name() + "' not set");            
                yield value;
            }
        };
    }

    */
