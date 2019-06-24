package osac.digiponic.com.osac.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import osac.digiponic.com.osac.model.DataMember;
import osac.digiponic.com.osac.repository.MemberRepository;

public class MemberActivityViewModel extends ViewModel {

    private MutableLiveData<List<DataMember>> mMemberData;
    private MemberRepository memberRepository;

    public void init() {
        if (mMemberData != null) {
            return;
        }

        memberRepository = MemberRepository.getInstance();
        memberRepository.initRetrofit();
        mMemberData = memberRepository.getAllDataMember();

    }

    public LiveData<List<DataMember>> getMember() {
        return mMemberData;
    }
}
