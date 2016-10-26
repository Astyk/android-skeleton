package com.github.willjgriff.skeleton.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 17/08/2016.
 */

public class SettingsFragment extends Fragment {


}
