package management.program.member;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String id;
    private String pwd;
    private String name;
    private String tel;
    private String address;
    private String birth;
    private String job;
    private String gender;
    private String email;
    private String intro;
}
