import {Component, OnInit, ViewChild} from '@angular/core';
import {SectorService} from '../../shared/services/sector.service';
import {UserService} from '../../shared/services/user.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Sector} from '../../shared/models/sector';
import {User} from '../../shared/models/user';

const NESTED_SELECT_SPACE_NUM = 6;

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  @ViewChild('formElement') formElement;
  form: FormGroup;
  sectorOptions: Array<Sector> = [];
  errors = {
    NAME_IS_USED: {
      formName: 'name',
      formError: 'nameIsUsed',
      errorText: 'user already exists with this name'
    },
    FIELD_IS_REQUIRED: {
      formError: 'required',
      errorText: 'The field is required'
    },
    TERMS_NOT_AGREED: {
      formError: 'notAgreed',
      errorText: 'Please agree to terms'
    },
    USER_NOT_FOUND: {
      formError: 'userNotFound',
      errorText: 'User not found'
    },
    INVALID_LENGTH: {
      formError1: 'minlength',
      formError2: 'maxlength',
      errorText: 'name length should be from 3 to 20 characters'
    }
  };
  addedSuccessfully: boolean;
  updatedSuccessfully: boolean;

  nameForLoadControl;
  loadedUser: User;

  constructor(
    private _sectorService: SectorService,
    private _userService: UserService,
    private _fb: FormBuilder
  ) { }

  ngOnInit() {
    this.form = this._fb.group({
      name: this._fb.control('', Validators.compose([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(30)
      ])),
      sectorIds: this._fb.control('', Validators.required),
      agreeToTerms: this._fb.control('', Validators.requiredTrue),
    });
    this.nameForLoadControl = this._fb.control('', Validators.required);
    this._sectorService.getSectors().subscribe(resp => {
      this.sectorOptions = resp;
    })
  }

  getSectorNameWithSpaces(sector: Sector): string {
    let whiteSpaces = '';
    for (let i = 0; i < this.countSectorParents(sector); i++) {
      for (let j = 0; j < NESTED_SELECT_SPACE_NUM; j++) {
        whiteSpaces += '\u00A0';
      }
    }
    return whiteSpaces + sector.name;
  }

  countSectorParents(sector: Sector): number {
    if (sector.parent)
      return 1 + this.countSectorParents(sector.parent);
    return 0;
  }

  saveUser() {
    this.addedSuccessfully = false;
    if (this.form.invalid) {
      this.form.get('agreeToTerms').setErrors({notAgreed: true});
      return;
    }
    if (!this.loadedUser) {
      this.addUser();
    } else {
      this.updateUser();
    }
  }

  private addUser() {
    this._userService.addUser(this.form.value).subscribe(() => {
      this.addedSuccessfully = true;
      this.formElement.resetForm();
    }, error => {
      error.error.errors.forEach(err => this.handleError(err));
    });
  }

  private updateUser() {
    let userUpdate = this.form.value;
    userUpdate.id = this.loadedUser.id;
    this._userService.update(userUpdate).subscribe(() => {
      this.updatedSuccessfully = true;
    }, error => {
      error.error.errors.forEach(err => this.handleError(err));
    });
  }

  loadUser() {
    this.nameForLoadControl.markAsTouched();
    if (!this.nameForLoadControl.valid) {
      return;
    }
    this._userService.getUserByName(this.nameForLoadControl.value).subscribe(user => {
      if (!user) {
        let error = {};
        error[this.errors.USER_NOT_FOUND.formError] = true;
        this.nameForLoadControl.setErrors(error);
      }
      this.form.get('name').setValue(user.name);
      this.form.get('sectorIds').setValue(user.sectors.map(sector => sector.id));
      this.form.get('agreeToTerms').setValue(user.agreeToTerms);
      this.loadedUser = user;
    }, error => {
      error.error.errors.forEach(err => this.handleError(err));
    });
  }

  reset() {
    delete this.loadedUser;
    this.addedSuccessfully = false;
    this.updatedSuccessfully = false;
    this.formElement.resetForm();
  }

  private handleError(err) {
    let knownError = this.errors[err.keyword];
    if (knownError) {
      let error = {};
      error[knownError.formError] = true;
      this.form.get(knownError.formName).setErrors(error);
    } else {
      console.error(err);
    }
  }
}
