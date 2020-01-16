package fr.mrartichaud.sheeppvp.kits;

public class SheepClasse implements Comparable<SheepClasse> {
    public String name;
    public String displayName;
    public SheepKit[] sheepKits;

    public SheepClasse(String name, String displayName, SheepKit[] sheepKits) {
        this.name = name;
        this.displayName = displayName;
        this.sheepKits = sheepKits;
    }

    @Override
    public int compareTo(SheepClasse o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
