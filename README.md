<img src="/documents/logo.png" alt="Johnny" style="width:100px; height:100px" />

# Schachspiel

In dieser Dokumentation wird der Spielablauf erklärt.

## Spielstart

Das Programm kann durch eine Konsoleneingabe gestartet werden. Die reine Konsolenausgabe wird mittels

````shell
schach --no-gui
````

gestartet. Es ist ebenso eine 2D-GUI zur Steuerung des Spiels enthalten. Dazu muss der Startparameter wieder entfernt werden. Im Folgenden wird zunächst nur von der Benutzung der --no-gui-Variante ausgegangen.

Schlussendlich gibt es zum einfachen Testen des Spiels ein 'Simple'-Flag, das mit

````shell
schach --no-gui --simple
````

aufgerufen wird.

Zu Beginn muss der Spieler einige Einstellungen treffen, bevor er das eigentliche Spiel starten kann.

### Sprachauswahl

Das Basisspiel ist in englischer Sprache, jedoch kann jeder Zeit in der GUI-Version per Knopfdruck auf die Flagge die Sprache auf Deutsch gewechselt werden. In der Konsolenversion kann dies über Eingabe von **language** oder **sprache** gesteuert werden.


### Initialisierung in der GUI Version

Die GUI-Version startet mit dem Hauptmenü, bei dem **The board with the figures** (tbWTF) aufgerufen wird.

<img src="/documents/main.png" alt="main" style="width:10px; height:10px" />

Hier kann der Spieler sich entscheiden, ob er eine Partie **Mensch vs Mensch** oder **Mensch vs Computer** starten möchte. Sucht der Spieler die Herausforderung gegen die KI, so wird er gebeten, sich für einen Schweregrad der KI (einfach, schwer, oder im Verlauf des Spiels sehr herausfordernd) und seine Farbe zu entscheiden.

<img src="/documents/query.png" alt="query" style="width:10px; height:10px" />

Man kann verschiedene Optionen per Auswahlmenü (oben rechter Button) an- und abwählen:

* **Show move possibilites** Schalte die Vorschau für Bewegungsmöglichkeiten einer ausgewählten Spielfigur an oder aus
* **Lock the chosen figure** Die Figur, die als erstes ausgewählt wurde, muss auch gespielt werden
* **Turn field** Drehe das Spielfeld, sodass die aktive Spielfarbe für den Zug unten steht
* **Display check** Zeige an, ob ein König im Schach steht

<img src="/documents/game.png" alt="game" style="width:10px; height:10px" />

Im Spielverlauf können überdies weitere Funktionalitäten genutzt werden, die auf der linken Seite zur Auswahl stehen:

* **Undo/Rückgängig** Mache den letzten Zug rückgängig; Beim Spielen gegen die KI wird auf den letzten Zug des menschlichen Spielers zurückgegangen
* **Redo/Wiederholen** Der Zug wird wiederholt.
* **Surrender/Aufgeben** Der aktive Spieler gibt auf. Die KI wird niemals aufgeben.
* **Draw/Remis** Der aktive Spieler bietet ein Remis an.

Spielsituationen können auch durch Anklicken der Zughistorie auf der rechten Seite ausgewählt werden. Redo/Undo sind nur möglich, solange der aktive Spieler noch keinen weiteren Zug getätigt hat.

### Initialisierung in der no-GUI Version (Konsole) 

Zu Beginn muss der Spieler einige Einstellungen treffen, bevor er das Spiel starten kann.

#### Sprachauswahl
Der Spieler wird gebeten, die aktuelle Sprache zu wählen. Dies kann jederzeit gewechselt werden durch Eingabe von **language** oder **sprache**.

#### Hauptmenü

Im Hauptmenü hat der Spieler die Wahl, ein neues Spiel zu starten oder das Spiel zu beenden und zurück zur Konsoleneingabe zu gelangen. 

#### Gegner wählen

Es kann entweder gegen einen anderen Menschen (HotSeat) oder gegen eine KI gespielt werden. Nach der Entscheidung, gegen eine KI zu spielen, muss zudem ausgewählt werden, gegen welche KI.

#### Auswahl der Farbe von Spieler 1

Wurde eine KI als Gegner gewählt, so kann der Spieler entscheiden, ob er anfängt (weiße Farbe) oder die KI anfangen soll (schwarze Farbe).

#### Spielmodus

Es besteht die möglichkeit, ein Schachspiel mit bekannten Regeln ohne Zeitlimit zu starten:

* Lass deinen Entscheidungen Zeit! Dieser Modus ist für eine ruhige Partie in der Vorlesung, wenn du und dein Mitspieler doch ab und zu mal zuhören müssen, was dein Dozent dir versucht zu erklären. 

#### Das Spiel in der Konsole

Willkommen auf dem Konsolen-Schlachtfeld! Zur schnellen Orientierung und Entwicklung einer Erfolgsstrategie wird den Spielern das Schachbrett überragend übersichtlich präsentiert:

````shell
8 r n b q k b n r
7 p p p p p p p p
6
5
4
3
2 P P P P P P P P 
1 R N B Q K B N R
  a b c d e f g h 
````

* Der weiße Spieler (unten) hat Figuren in GROSSBUCHSTABEN, der schwarze Spieler in kleinbuchstaben. 
* Bedeutung der Spielfiguren: P/p - Bauer, R/r - Turm, N/n - Springer, B/b - Läufer, Q/q - Königin, K/k - König

Das Spiel beginnt. Gültige Eingaben sehen wie folgt aus: "a2-a4" würde z.B. den Bauern auf dem Feld A2 auf das Feld A4 bewegen. Der Bindestrich trennt immer die Start- von den Zielkoordinaten.
Um einen Bauern, der die letzte Zeile erreicht umzuwandeln, muss nach der Eingabe der Koordinaten die entsprechende Figur eingegeben werden, z.B. "a7-a8Q", um den weißen Bauern, der von A7 auf A8 gezogen wird, in eine Dame umzuwandeln. Für die anderen Spielfiguren muss das entsprechende Kürzel eingegeben werden (s.o.).
Ungültige Züge und falsche Eingaben führen zur Ausgabe einer Fehlermeldung. Das Programm wartet dann weiterhin auf eine gültige Eingabe des Spielers, der an der Reihe ist.
Die Eingabe "language" oder "sprache" in die Konsole verursacht einen Wechsel der Anzeigesprache.
Gibt ein Spieler "beaten" ein, werden ihm die bereits geschlagenen Figuren angezeigt.

Weiß zieht zuerst. Danach wird abwechselnd gezogen.

Auch hier stehen eine Reihe Optionen zur Verfügung, die durch Eingabe des Befehls gewählt werden können:

* **undo/rückgängig** Mache den letzten Zug rückgängig; Beim Spielen gegen die KI wird auf den letzten Zug des menschlichen Spielers zurückgegangen. Wird während des Denkens der KI undo eingegeben, beendet die KI ihren Zug und es wird anschließend auf den letzten menschlichen Zug zurückgesetzt.
* **redo/wiederholen** Der Zug wird wiederholt. Auch Benachrichtugungen werden wiederholt. 
* **surrender/aufgeben** Der aktive Spieler gibt auf. Die KI wird niemals aufgeben.
* **draw/remis** Der aktive Spieler bietet ein Remis an. 

Eingaben werden hier mittels entsprechender Ausgaben quittiert. redo/undo sind nur möglich, solange der aktive Spieler noch keinen weiteren Zug getätigt hat.


### Die KI - Johnny Rook

Der Spieler hat die Auswahl zwischen einer leichten, jedoch sehr aggressiven KI, sowie einer schweren KI namens Johnny Rook, die über die Konsequenzen ihrer Züge nachdenkt. Als dritter Modus gibt es eine Erweiterung der KI, die im Verlauf des Spiels überlegener wird, jedoch dafür auch mehr Rechenzeit in Anspruch nimmt. Das Arbeiten der KI wird mittels des Signals **Thinking.....** dargestellt. 

### Klappentext 

Versuch die Verteidigungslinien deines Gegners einzuschnüren und vernichte seinen König! Aber pass auf, dass du dich nicht verguckst, sonst stehst du schnell mit dem Rücken zur Wand!!!

Das Spiel ist vorbei, wenn ein König schachmatt gesetzt wird. Nicht weniger spektakulär ist die Möglichkeit zu einem Unentschieden.

### Allgemeine Regeln

Es wird nach den [Regeln des Deutschen Schachbundes](https://www.schachbund.de/files/dsb/srk/2019/FIDE-Regeln-2018-Final-DEU.pdf "Regelwerk des DSB") gespielt. Sonderzüge wie das Schlagen en passant oder die Rochade sind erlaubt.

## Trivia

* ![Johnny Rook](https://projects.isp.uni-luebeck.de/swolff/schach/-/blob/master/documents/logo.png) ist der englische Name des Falklandkarakaras und Schutzpatron unseres Codes.
* Man kann gegen Johnny Rook als KI spielen. Aber achtung, er pickt. 


