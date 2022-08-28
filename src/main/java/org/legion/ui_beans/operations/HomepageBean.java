package org.legion.ui_beans.operations;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.ui_beans.ParentBean;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@Slf4j
@Getter
@Setter
public class HomepageBean extends ParentBean implements Serializable {

}
