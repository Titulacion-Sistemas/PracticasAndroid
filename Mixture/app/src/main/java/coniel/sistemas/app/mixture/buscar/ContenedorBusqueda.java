package coniel.sistemas.app.mixture.buscar;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coniel.sistemas.app.mixture.Contenedor;
import coniel.sistemas.app.mixture.R;
import coniel.sistemas.app.mixture.buscar.classes.Abonado;


public class ContenedorBusqueda extends Fragment {

    String[] sesion = null;


    View rootView;

    private FragmentTabHost tabHost;

    private Abonado[] clientes=null;

    public Abonado[] getClientes() {
        return clientes;
    }

    public void setClientes(Abonado[] clientes) {
        this.clientes = clientes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_busqueda_contenedor);

        Bundle arg1 = new Bundle();
        arg1.putInt("Arg for Frag1", 1);
        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Buscar"),
                Buscar.class, arg1);

        Bundle arg2 = new Bundle();
        arg2.putInt("Arg for Frag2", 2);
        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Abonado"),
                BusquedaDatosAbonado.class, arg2);

        Bundle arg3 = new Bundle();
        arg2.putInt("Arg for Frag3", 3);
        tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Medidores"),
                BusquedaDatosMedidor.class, arg3);


        try{
            sesion = getActivity().getIntent().getExtras().getStringArray("user");
        } catch (Exception e) {
            Log.e("Error al Cargar datos de sesion: ", "" + e);
        }

        rootView = inflater.inflate(R.layout.fragment_busqueda_contenedor, container, false);

        tabHost.setBackgroundColor(0xffc2f6fb);

        return tabHost;

    }

    /*public void reconstruirPager(int seleccionado){
        MiPagerAdapter sa = (MiPagerAdapter) mViewPager.getAdapter();
        ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
        pagerItems.add(new PagerItem("Fragment0", sa.getmPagerItems().get(0).getFragment()));
        sa.notifyDataSetChanged();
        mViewPager.setAdapter(new MiPagerAdapter(
                getActivity().getSupportFragmentManager(),
                pagerItems
        ));
        sa = (MiPagerAdapter) mViewPager.getAdapter();
        ((Buscar)sa.getItem(0)).cambiarOpcion(seleccionado);
    }*/
/*
    public void cargarPager() {
        MiPagerAdapter sa = (MiPagerAdapter) mViewPager.getAdapter();
        sa.setAb(((Buscar) sa.getItem(0)).getClientes());

        ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
        pagerItems.add(new PagerItem("Fragment0", sa.getItem(0)));
        pagerItems.add(new PagerItem("Fragment1", new BusquedaDatosAbonado(sa.getAb()[0])));
        pagerItems.add(new PagerItem("Fragment2", new BusquedaDatosMedidor(sa.getAb()[0].getMedidores())));
        sa.setmPagerItems(pagerItems);
        sa.notifyDataSetChanged();


        mViewPager.setAdapter(
                new MiPagerAdapter(
                        getActivity().getSupportFragmentManager(),
                        pagerItems
                )
        );
    }*/

    /*public class MiPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager mFragmentManager;
        private ArrayList<PagerItem> mPagerItems;
        private Abonado[] ab = null;

        public MiPagerAdapter(FragmentManager fragmentManager, ArrayList<PagerItem> pagerItems) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
            setmPagerItems(pagerItems);
        }


        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            return getmPagerItems().get(position).getFragment();
            *//*Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment = new Buscar(sesion);
                    args = new Bundle();
                    args.putInt(Buscar.ARG_SECTION_NUMBER, position + 1);
                    break;
                case 1:
                    if (ab!=null) fragment = new BusquedaDatosAbonado(ab[0]);
                    else fragment = new BusquedaDatosAbonado();
                    args = new Bundle();
                    args.putInt(BusquedaDatosAbonado.ARG_SECTION_NUMBER, position + 1);
                    //args.putString(FragmentGraph.linkGraph, graphLink);
                    break;
                case 2:
                    if (ab!=null) fragment = new BusquedaDatosMedidor(ab[0].getMedidores());
                    else fragment = new BusquedaDatosMedidor();
                    args = new Bundle();
                    args.putInt(BusquedaDatosMedidor.ARG_SECTION_NUMBER, position + 1);
                    break;
            }
            fragment.setArguments(args);
            return fragment;*//*
        }

        @Override
        public int getCount() {
            return mPagerItems.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public ArrayList<PagerItem> getmPagerItems() {
            return mPagerItems;
        }

        public void setmPagerItems(ArrayList<PagerItem> mPagerItems) {
            this.mPagerItems = mPagerItems;
        }

        public Abonado[] getAb() {
            return ab;
        }

        public void setAb(Abonado[] ab) {
            this.ab = ab;
        }


    }*/
/*

    //implements on pager selected
    @Override
    public void onPageScrolled(int i, float v, int i2) {  }


    @Override
    public void onPageSelected(int i) {
        getActivity().getActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}




    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        if(((MiPagerAdapter)mViewPager.getAdapter()).getmPagerItems().size()>tab.getPosition()) {
            mViewPager.setCurrentItem(tab.getPosition());
        }else{
            Toast t = Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Debe realizar una busqueda...",
                    Toast.LENGTH_SHORT
            );
            t.show();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }
*/


    @Override
    public void onDetach() {

        ((Contenedor)getActivity()).setmTitle(getString(R.string.app_name));
        ((Contenedor)getActivity()).setmSubTitle(getString(R.string.app_Suntitle));
        ((Contenedor)getActivity()).displayView(0, null);

        clientes=null;

        super.onDetach();
        Log.i("Información", "Dtach de Fotos");
    }
}