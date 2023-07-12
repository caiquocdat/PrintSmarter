package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Compatible_List_Activity extends AppCompatActivity {
    RelativeLayout astroMedRel,auroraRel,brotherRel,canonRel,conexantRel,deliRel,dellRel,developRel,esponRel,fRel,fujiRel,fujifilmRel,ggRel,gestetnerRel,hpRel,infotecRel,jiyinRel,kodakRel,konicaRel,kyoceraRel,lanierRel,lenovoRel,
    lexmarkRel,lgRel,miRel,muratecRel,necRel,nrgRel,nttRel,okiRel,olivettiRel,panasoicRel,pantumRel,princiaoRel,prinkRel,ricohRel,
    rolloRel,samsungRel,savinRel,sharpRel,sindohRel,startRel,taRel,toshibaRel,xeroxRel,zinkRel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatible_list);
        mapping();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Compatible List");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.img_back);
            getSupportActionBar().setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }
        astroMedRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Astro"); // cặp key-value
                startActivity(intent);
            }
        });
        auroraRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Aurora"); // cặp key-value
                startActivity(intent);
            }
        });
        brotherRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Brother"); // cặp key-value
                startActivity(intent);
            }
        });
        canonRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Canon"); // cặp key-value
                startActivity(intent);
            }
        });
        conexantRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Conexant"); // cặp key-value
                startActivity(intent);
            }
        });
        deliRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Deli"); // cặp key-value
                startActivity(intent);
            }
        });
        dellRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Dell"); // cặp key-value
                startActivity(intent);
            }
        });
        developRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Develop"); // cặp key-value
                startActivity(intent);
            }
        });
        esponRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "Epson"); // cặp key-value
                startActivity(intent);
            }
        });
        fRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "f"); // cặp key-value
                startActivity(intent);
            }
        });
        fujiRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "fuji"); // cặp key-value
                startActivity(intent);
            }
        });
        fujifilmRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "fujifilm"); // cặp key-value
                startActivity(intent);
            }
        });
        ggRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "gg"); // cặp key-value
                startActivity(intent);
            }
        });
        gestetnerRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "gestetner"); // cặp key-value
                startActivity(intent);
            }
        });
        hpRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "hp"); // cặp key-value
                startActivity(intent);
            }
        });
        infotecRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "infotec"); // cặp key-value
                startActivity(intent);
            }
        });
        jiyinRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "jiyin"); // cặp key-value
                startActivity(intent);
            }
        });
        kodakRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "kodak"); // cặp key-value
                startActivity(intent);
            }
        });
        konicaRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "konica"); // cặp key-value
                startActivity(intent);
            }
        });
        kyoceraRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "kyocera"); // cặp key-value
                startActivity(intent);
            }
        });
        lanierRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "lanier"); // cặp key-value
                startActivity(intent);
            }
        });
        lenovoRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "lenovo"); // cặp key-value
                startActivity(intent);
            }
        });
        lexmarkRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "lexmark"); // cặp key-value
                startActivity(intent);
            }
        });
        lgRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "lg"); // cặp key-value
                startActivity(intent);
            }
        });
        miRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "mi"); // cặp key-value
                startActivity(intent);
            }
        });
        muratecRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "muratec"); // cặp key-value
                startActivity(intent);
            }
        });
        necRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "nec"); // cặp key-value
                startActivity(intent);
            }
        });
        nrgRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "nrg"); // cặp key-value
                startActivity(intent);
            }
        });
        nttRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "ntt"); // cặp key-value
                startActivity(intent);
            }
        });
        okiRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "oki"); // cặp key-value
                startActivity(intent);
            }
        });
        olivettiRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "olivetti"); // cặp key-value
                startActivity(intent);
            }
        });
        panasoicRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "panasonic"); // cặp key-value
                startActivity(intent);
            }
        });
        pantumRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "pantum"); // cặp key-value
                startActivity(intent);
            }
        });
        princiaoRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "princiao"); // cặp key-value
                startActivity(intent);
            }
        });
        prinkRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "prink"); // cặp key-value
                startActivity(intent);
            }
        });
        ricohRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "ricoh"); // cặp key-value
                startActivity(intent);
            }
        });
        rolloRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "rollo"); // cặp key-value
                startActivity(intent);
            }
        });
        samsungRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "samsung"); // cặp key-value
                startActivity(intent);
            }
        });
        savinRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "savin"); // cặp key-value
                startActivity(intent);
            }
        });
        sharpRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "sharp"); // cặp key-value
                startActivity(intent);
            }
        });
        sindohRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "sindoh"); // cặp key-value
                startActivity(intent);
            }
        });
        startRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "star"); // cặp key-value
                startActivity(intent);
            }
        });
        taRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "ta"); // cặp key-value
                startActivity(intent);
            }
        });
        toshibaRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "toshiba"); // cặp key-value
                startActivity(intent);
            }
        });
        xeroxRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "xerox"); // cặp key-value
                startActivity(intent);
            }
        });
        zinkRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compatible_List_Activity.this, Compatible_List_Detail_Activity.class);
                intent.putExtra("type", "zink"); // cặp key-value
                startActivity(intent);
            }
        });









    }

    private void mapping() {
        astroMedRel=findViewById(R.id.astroMedTv);
        auroraRel=findViewById(R.id.auroraTv);
        brotherRel=findViewById(R.id.brotherTv);
        canonRel=findViewById(R.id.canonTv);
        conexantRel=findViewById(R.id.conexantTv);
        deliRel=findViewById(R.id.deliTv);
        dellRel=findViewById(R.id.dellTv);
        developRel=findViewById(R.id.developTv);
        esponRel=findViewById(R.id.epsonTv);
        fRel=findViewById(R.id.fTv);
        fujiRel=findViewById(R.id.fujiTv);
        fujifilmRel=findViewById(R.id.fujifilmTv);
        ggRel=findViewById(R.id.ggTv);
        gestetnerRel=findViewById(R.id.gestetnerTv);
        hpRel=findViewById(R.id.hpTv);
        infotecRel=findViewById(R.id.infotecTv);
        jiyinRel=findViewById(R.id.jiyinTv);
        kodakRel=findViewById(R.id.kodakTv);
        konicaRel=findViewById(R.id.konicaTv);
        kyoceraRel=findViewById(R.id.kyoceraTv);
        lanierRel=findViewById(R.id.lanierTv);
        lenovoRel=findViewById(R.id.lenovoTv);
        lexmarkRel=findViewById(R.id.lexmarkTv);
        lgRel=findViewById(R.id.lgTv);
        miRel=findViewById(R.id.miTv);
        muratecRel=findViewById(R.id.muratecTv);
        necRel=findViewById(R.id.necTv);
        nrgRel=findViewById(R.id.nrgTv);
        nttRel=findViewById(R.id.nttTv);
        okiRel=findViewById(R.id.okiTv);
        olivettiRel=findViewById(R.id.olivettiTv);
        panasoicRel=findViewById(R.id.panasonicTv);
        pantumRel=findViewById(R.id.pantumTv);
        princiaoRel=findViewById(R.id.princiaoTv);
        prinkRel=findViewById(R.id.prinkTv);
        ricohRel=findViewById(R.id.ricohTv);
        rolloRel=findViewById(R.id.rolloTv);
        samsungRel=findViewById(R.id.samsungTv);
        savinRel=findViewById(R.id.savinTv);
        sharpRel=findViewById(R.id.sharpTv);
        sindohRel=findViewById(R.id.sindohTv);
        startRel=findViewById(R.id.starTv);
        taRel=findViewById(R.id.taTv);
        toshibaRel=findViewById(R.id.toshibaTv);
        xeroxRel=findViewById(R.id.xeroxTv);
        zinkRel=findViewById(R.id.zinkTv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}