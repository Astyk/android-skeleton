package com.github.willjgriff.skeleton.data;

import rx.Observable;

/**
 * Created by Will on 31/12/2016.
 */

public interface RefreshableRepository {

	void setRefreshTrigger(Observable<Void> refreshTrigger);
}
