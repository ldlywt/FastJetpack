package com.fastaac;

import android.os.Bundle;
import android.view.View;

import com.fastaac.base.base.BaseMvvMFragment;
import com.fastaac.databinding.FragmentTestBinding;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/03/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestFragment extends BaseMvvMFragment<MainVm, FragmentTestBinding> {


    public TestFragment() {
        super(R.layout.fragment_test);
    }

    @Override
    public FragmentTestBinding initBinding(View view) {
        return FragmentTestBinding.bind(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.text.setText("ViewBinding封装");
    }
}
