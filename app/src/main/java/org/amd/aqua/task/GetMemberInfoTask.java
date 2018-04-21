package org.amd.aqua.task;

import android.app.Activity;

/**
 * Created by Akira on 2018/04/21.
 */

public abstract class GetMemberInfoTask extends AquaApiTask {

    protected String ownerId;

    public GetMemberInfoTask(Activity activity, String ownerId) {
        super(activity);
        this.ownerId = ownerId;
    }


}