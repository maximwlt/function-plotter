## Ausführungsanleitung LVP mit dem Code
**Ablauf:**
1. Installieren Sie mindestens die JDK24-Version
2. Entpacken Sie das ZIP-Archiv in einen Ordner ihrer Wahl
3. Öffnen Sie diesen Ordner in einer IDE (wie z.B: VisualStudioCode) ihrer Wahl
4. Öffnen Sie anschließend ein neues Terminal (in ihrer IDE) und geben sie folgenden Befehl ein: 
    `java -jar lvp-0.5.4.jar --log --watch=funktionsplotter.java` <br>
    LVP startet mit der `funktionsplotter.java` und ist unter dem `http://localhost:50001/` aufrufbar

**Modifikationen am LVP: <br>**
Es wurde lediglich die [Turtle.java](https://github.com/denkspuren/LiveViewProgramming/blob/main/src/main/java/lvp/views/Turtle.java) aus dem [*LiveViewProgramming*](https://github.com/denkspuren/LiveViewProgramming) gezogen und einige Methoden hinzugefügt für einen
vereinfachten bzw. angenehmeren Umgang mit der Turtle. Die Turtle, die aus der JAR-Datei gegeben ist wird im Programm nicht importiert.

**Erweiterungen:**
1. Eingabe und Anzeige von mehreren Funktionen, die farblich voneinander unterschieden werden
2. Funktionen mit Parameter a, wo die Anzahl an Werten selbst gewählt werden kann und gleichzeitig die verschiedenen Funktionsverläufe anzeigt werden.

**Wichtige Bemerkung**: Das ZIP-Archiv enthält vier Dateien: <br>
    1. *funktionsplotter.java* --> Das Java-Programm für den Funktionsplotter<br>
    2. *lvp-0.5.4.jar* --> Die JAR-Datei für das LVP <br>
    3. *Turtle.java* --> Selbstständig erweiterte Turtle mit 3 zusätzlichen Methoden, die anstelle der Turtle aus der JAR-Datei (*lvp-0.5.4.jar*) verwendet wird<br>
    4. *README.md* --> Informationen darüber, wie man das Programm ans Laufen bekommt<br>

Bei der Ausführung beachten, dass alle Dateien gegeben sind und Sie sich im richtigen Verzeichnis befinden!