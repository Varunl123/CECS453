package com.example.project2;

// OnButtonPressListener is an interface having three callback methods which are helpful in communication between ButtonFragment.java and MainActivity.java
public interface OnButtonPressListener {
    void onButtonPressed(Integer index);
    void onSlideShowCheck(Boolean check);
    void onGallaryViewCheck(Boolean check);

}
