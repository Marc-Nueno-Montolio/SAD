# Software per Aplicacions Distribuïdes
En aquest repositori es poden trobar les pràctiques de l'assignatura SAD realitzades per Marina Morgado i Marc Nueno el 2022.

S'han desenvolupat 3 pràctiques en Java i 1 projecte en Python.
Pràctiques en Java:
- EditableBufferedReader. Aplicació de consola per editar text en raw mode.
- Aplicació Client/Servidor d'un Xat. Client textual.
- Aplicació Client/Servidor d'un Xat. Client gràfic amb Swing.

Projecte Final:
- SAD Project. Una aplicació web desenvolupada en Python fent servir el patró  MVC per facilitar la reserva de material esportiu.
## EditableBufferedReader
És un miniprojecte en el que es treballa el patró MVC, parsing de seqüències d'escape d'un emulador de terminal i execució de programes externs des de JAVA.

S'han desenvolupat varies versions:
### Versió noMVC
Primera versió del programa, que permet introduïr una línea de text en mode raw i permet la seva edició utilitzant tecles com endavant, enrere, fi, suprimir... Fa servir parsing de seqüències d'escapament.
[Veure codi](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/EditableBufferedReader/noMVC)

### Versió MVC
En aquesta versió s'ha implementat el patrò MVC fent servir Observer/Observa le i s'ha fet una refactorització del codi.
[Veure codi](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/EditableBufferedReader/MVC)

### Versió RatolíMVC
En aquesta versió, a més de tota la funcionalitat de la versió MVC, s'ha afegit la possibilitat de situar-se a qualsevol lloc fent servir el cursor del ratolí. S'han fet servir seqüències d'escapament Xterm.
[Veure codi](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/EditableBufferedReader/RatoliMVC)

## Client textual
Consisteix en el disseny del client i servidor d'un programa de Xat. 

## Client gràfic amb Swing
Es programarà el client de Xat amb entorn gràfic Swing. 

## SADProject
