package ceui.lisa.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import ceui.lisa.activities.Shaft;
import ceui.lisa.activities.ViewPagerActivity;
import ceui.lisa.adapters.BaseAdapter;
import ceui.lisa.adapters.IAdapter;
import ceui.lisa.databinding.FragmentBaseListBinding;
import ceui.lisa.databinding.RecyIllustStaggerBinding;
import ceui.lisa.http.Retro;
import ceui.lisa.interfaces.NetControl;
import ceui.lisa.model.IllustsBean;
import ceui.lisa.model.ListIllustResponse;
import ceui.lisa.utils.DensityUtil;
import ceui.lisa.utils.IllustChannel;
import ceui.lisa.view.SpacesItemDecoration;
import io.reactivex.Observable;

public class FragmentLatestWorks extends NetListFragment<FragmentBaseListBinding, ListIllustResponse,
        IllustsBean, RecyIllustStaggerBinding> {

    private String workType;

    public static FragmentLatestWorks newInstance(String paramWorkType) {
        Bundle args = new Bundle();
        args.putString("dataType", paramWorkType);
        FragmentLatestWorks fragment = new FragmentLatestWorks();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        workType = bundle.getString("dataType");
    }

    @Override
    public NetControl<ListIllustResponse> present() {
        return new NetControl<ListIllustResponse>() {
            @Override
            public Observable<ListIllustResponse> initApi() {
                return Retro.getAppApi().getNewWorks(Shaft.sUserModel.getResponse().getAccess_token(), workType);
            }

            @Override
            public Observable<ListIllustResponse> initNextApi() {
                return Retro.getAppApi().getNextIllust(Shaft.sUserModel.getResponse().getAccess_token(), nextUrl);
            }
        };
    }

    @Override
    public BaseAdapter<IllustsBean, RecyIllustStaggerBinding> adapter() {
        return new IAdapter(allItems, mContext).setOnItemClickListener((v, position, viewType) -> {
            IllustChannel.get().setIllustList(allItems);
            Intent intent = new Intent(mContext, ViewPagerActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @Override
    public void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        baseBind.recyclerView.setLayoutManager(layoutManager);
        baseBind.recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dp2px(8.0f)));
    }
}
