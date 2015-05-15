package team.monroe.org.pocketfit.fragments;

import org.monroe.team.android.box.app.FragmentSupport;

import team.monroe.org.pocketfit.Dashboard;
import team.monroe.org.pocketfit.PocketFitApp;

public abstract class BodyFragment  extends FragmentSupport<PocketFitApp> {

    private HeaderUpdateRequest headerUpdateRequest = HeaderUpdateRequest.NOT_SET;

    public void feature_headerUpdate(HeaderUpdateRequest headerUpdateRequest) {
        this.headerUpdateRequest = headerUpdateRequest;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (headerUpdateRequest != HeaderUpdateRequest.NOT_SET){
            if (headerUpdateRequest == HeaderUpdateRequest.SET){
                ((Dashboard) activity()).header(getHeaderName(), isHeaderSecondary());
            }else {
                ((Dashboard) activity()).animateHeader(getHeaderName(), isHeaderSecondary());
            }
        }
        headerUpdateRequest = HeaderUpdateRequest.NOT_SET;
    }

    protected abstract boolean isHeaderSecondary();
    protected abstract String getHeaderName();

    public static enum HeaderUpdateRequest {
        SET, NOT_SET, ANIMATE
    }

}
