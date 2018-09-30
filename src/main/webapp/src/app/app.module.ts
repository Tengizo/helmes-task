/* Angular modules*/
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
/* Core modules*/

/*external library modules*/
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule, MatInputModule,
  MatSelectModule,
  MatToolbarModule
} from '@angular/material';
import {FlexLayoutModule} from '@angular/flex-layout';
/*external library modules*/

/*Components*/
import {AppComponent} from './app.component';
import {MainComponent} from './main/main/main.component';
/*Components*/

/*Services*/
import {SectorService} from './shared/services/sector.service';
import {UserService} from './shared/services/user.service';

/*Services*/


@NgModule({
  declarations: [
    AppComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatCardModule,
    MatButtonModule,
    FlexLayoutModule
  ],
  providers: [SectorService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule {}
