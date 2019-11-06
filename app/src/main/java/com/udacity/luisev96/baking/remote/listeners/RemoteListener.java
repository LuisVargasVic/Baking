package com.udacity.luisev96.baking.remote.listeners;

public interface RemoteListener {
    void preExecute();
    void postExecute(Boolean isData);
}
