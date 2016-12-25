package com.github.willjgriff.skeleton.data.network;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by Will on 26/11/2016.
 */

public interface ListNetworkDataSource<TYPE, QUERY> {

	Observable<List<TYPE>> getDataFromNetwork(QUERY query);


}
