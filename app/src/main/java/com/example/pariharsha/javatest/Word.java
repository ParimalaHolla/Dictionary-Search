package com.example.pariharsha.javatest;

import java.util.List;

public class Word {
    private List<String> transformationPath;
    private String startWord;
    private int pathLength;

    public Word(List<String> path){
        this.transformationPath = path;
    }

    public Word(List<String> transformationPath, int pathLength, String lastWord){
        this.transformationPath = transformationPath;
        this.startWord = lastWord;
        this.pathLength = pathLength;
    }

    public List<String> getTransformationPath()
    {
        return transformationPath;
    }

    public void setTransformationPath(List<String> transformationPath)
    {
        this.transformationPath = transformationPath;
    }

    public String setStartWord() {
        return startWord;
    }

    public void setStartWord(String lastWord) {
        this.startWord = lastWord;
    }

    public int getPathLength()
    {
        return pathLength;
    }

    public void setPathLength(int pathLength)
    {
        this.pathLength = pathLength;
    }
}
